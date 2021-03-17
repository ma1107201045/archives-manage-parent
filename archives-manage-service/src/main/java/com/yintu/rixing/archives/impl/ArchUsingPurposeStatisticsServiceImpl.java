package com.yintu.rixing.archives.impl;

import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.ArchUsingPurposeStatisticsMapper;
import com.yintu.rixing.archives.IArchUsingPurposeStatisticsService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.dto.archives.ArchivesStatsQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.make.IMakeBorrowPurposeService;
import com.yintu.rixing.make.MakeBorrowPurpose;
import com.yintu.rixing.make.MakeBorrowPurposeMapper;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.archives.ArchArchivesQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchUsingPurposeDataVo;
import com.yintu.rixing.vo.archives.ArchUsingPurposeStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchivesCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/2/19 11:29:01
 * @Version: 1.0
 */
@Service
public class ArchUsingPurposeStatisticsServiceImpl extends ArchAbstractService implements IArchUsingPurposeStatisticsService {

    @Autowired
    private ArchUsingPurposeStatisticsMapper archUsingPurposeStatisticsMapper;
    @Autowired
    private IMakeBorrowPurposeService iMakeBorrowPurposeService;

    @Override
    public ArchUsingPurposeStatisticsDataVo findArchUsingPurposeStatisticsData(ArchCommonQueryDto archCommonQueryDto) {
        Integer departmentId = archCommonQueryDto.getDepartmentId();
        Date startDate = archCommonQueryDto.getStartDate();
        Date endDate = archCommonQueryDto.getEndDate();
        List<Integer> archivesIds = archCommonQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<MakeBorrowPurpose> makeBorrowPurposes = iMakeBorrowPurposeService.list();
        List<ArchUsingPurposeDataVo> archUsingPurposeDataVos = new ArrayList<>();
        for (MakeBorrowPurpose makeBorrowPurpose : makeBorrowPurposes) {
            Integer makeId1 = makeBorrowPurpose.getId();
            String name = makeBorrowPurpose.getName();
            List<Long> values = new ArrayList<>();
            for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
                Integer id = archivesLibrary.getId();
                String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
                List<Map<String, Object>> maps = archUsingPurposeStatisticsMapper.selectArchUsingPurposeStatisticsData(id, tableName, (short) 1, departmentId, startDate, endDate);
                Long count = null;
                for (Map<String, Object> map : maps) {
                    Integer makeId2 = (Integer) map.get("makeId");
                    if (makeId2.equals(makeId1)) {
                        count = (Long) map.get("count");
                    }
                }
                values.add(count == null ? 0L : count);
            }
            ArchUsingPurposeDataVo archUsingPurposeDataVo = new ArchUsingPurposeDataVo();
            archUsingPurposeDataVo.setName(name);
            archUsingPurposeDataVo.setValues(values);
            archUsingPurposeDataVos.add(archUsingPurposeDataVo);
        }
        ArchUsingPurposeStatisticsDataVo archUsingPurposeStatisticsDataVo = new ArchUsingPurposeStatisticsDataVo();
        archUsingPurposeStatisticsDataVo.setNames(archivesLibraries.stream().map(SysArchivesLibrary::getName).collect(Collectors.toList()));
        archUsingPurposeStatisticsDataVo.setArchUsingPurposeDataVos(archUsingPurposeDataVos);
        return archUsingPurposeStatisticsDataVo;
    }

    /**
     * 查询档案利用统计数据
     *
     * @param archivesStatsQueryDto
     * @return
     */
    @Override
    public List<ArchivesCommonVo> findArchivesInfo(ArchivesStatsQueryDto archivesStatsQueryDto) {
        List<ArchivesCommonVo> result = new ArrayList<>();
        Date startDate = archivesStatsQueryDto.getStartDate();
        Date endDate = archivesStatsQueryDto.getEndDate();
        List<Integer> archivesIds = archivesStatsQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<MakeBorrowPurpose> makeBorrowPurposes = iMakeBorrowPurposeService.list();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            Map<String, Object> dataMap = new HashMap<>();
            ArchivesCommonVo archivesCommonVo = new ArchivesCommonVo();
            Integer id = archivesLibrary.getId();
            String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
            List<Map<String, Object>> maps = archUsingPurposeStatisticsMapper.selectArchUsingPurposeStatisticsData(id, tableName, (short) 1, null, startDate, endDate);
            for (MakeBorrowPurpose makeBorrowPurpose : makeBorrowPurposes) {
                Integer makeId1 = makeBorrowPurpose.getId();
                String name = makeBorrowPurpose.getName();
                Long count = null;
                for (Map<String, Object> map : maps) {
                    Integer makeId2 = (Integer) map.get("makeId");
                    if (makeId2.equals(makeId1)) {
                        count = (Long) map.get("count");
                    }
                }
                dataMap.put(name,count == null ? 0L : count);
            }
            archivesCommonVo.setId(id);
            archivesCommonVo.setDataMap(dataMap);
            archivesCommonVo.setName(archivesLibrary.getName());
            archivesCommonVo.setType(archivesLibrary.getType());
            archivesCommonVo.setArchivesType(archivesLibrary.getArchType());
            result.add(archivesCommonVo);

        }
        return result;
    }

    /**
     * 导出档案利用统计数据
     *
     * @param response
     * @param archivesStatsQueryDto
     */
    @Override
    public void exportExcel(HttpServletResponse response, ArchivesStatsQueryDto archivesStatsQueryDto) {

    }
}
