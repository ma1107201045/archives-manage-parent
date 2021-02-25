package com.yintu.rixing.archives.impl;

import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.ArchArchivesQuantityStatisticsMapper;
import com.yintu.rixing.archives.IArchArchivesQuantityStatisticsService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.enumobject.EnumAuditStatus;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.archives.ArchArchivesQuantityStatisticsDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/1/30 14:22:25
 * @Version: 1.0
 */
@Service
public class ArchArchivesQuantityStatisticsServiceImpl extends ArchAbstractService implements IArchArchivesQuantityStatisticsService {
    @Autowired
    private ArchArchivesQuantityStatisticsMapper archArchivesQuantityStatisticsMapper;

    @Override
    public ArchArchivesQuantityStatisticsDataVo findArchArchivesQuantityStatisticsData(ArchCommonQueryDto archCommonQueryDto) {
        Integer departmentId = archCommonQueryDto.getDepartmentId();
        Date startDate = archCommonQueryDto.getStartDate();
        Date endDate = archCommonQueryDto.getEndDate();
        List<Integer> archivesIds = archCommonQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<List<Long>> lists = new ArrayList<>();
        List<Long> list1 = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        List<Long> list3 = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            Integer id = archivesLibrary.getId();
            String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
            Map<String, Long> map = archArchivesQuantityStatisticsMapper.selectArchivesQuantityStatisticsData(id, tableName, departmentId, startDate, endDate);
            if (map == null) {
                list1.add(0L);
                list2.add(0L);
                list3.add(0L);
            } else {
                list1.add(map.get("1"));
                list2.add(map.get("2"));
                list3.add(map.get("3"));
            }
        }
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        ArchArchivesQuantityStatisticsDataVo archArchivesQuantityStatisticsDataVo = new ArchArchivesQuantityStatisticsDataVo();
        archArchivesQuantityStatisticsDataVo.setNames(archivesLibraries.stream().map(SysArchivesLibrary::getName).collect(Collectors.toList()));
        archArchivesQuantityStatisticsDataVo.setValues(lists);
        return archArchivesQuantityStatisticsDataVo;
    }


}
