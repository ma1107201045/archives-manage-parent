package com.yintu.rixing.archives.impl;

import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.ArchDiseaseArchivesAppraisalMapper;
import com.yintu.rixing.archives.IArchDiseaseArchivesAppraisalService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.archives.ArchDiseaseArchivesAppraisalDataVo;
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
 * @Date: 2021/2/22 10:36:05
 * @Version: 1.0
 */
@Service
public class ArchDiseaseArchivesAppraisalServiceImpl extends ArchAbstractService implements IArchDiseaseArchivesAppraisalService {

    @Autowired
    private ArchDiseaseArchivesAppraisalMapper archDiseaseArchivesAppraisalMapper;

    @Override
    public ArchDiseaseArchivesAppraisalDataVo findArchDiseaseArchivesAppraisalData(ArchCommonQueryDto archCommonQueryDto) {
        Integer departmentId = archCommonQueryDto.getDepartmentId();
        Date startDate = archCommonQueryDto.getStartDate();
        Date endDate = archCommonQueryDto.getEndDate();
        List<Integer> archivesIds = archCommonQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<List<Long>> lists = new ArrayList<>();
        List<Long> list1 = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            Integer id = archivesLibrary.getId();
            String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
            Map<String, Long> map = archDiseaseArchivesAppraisalMapper.selectArchDiseaseArchivesAppraisalData(id, tableName, departmentId, startDate, endDate, EnumArchivesOrder.DISEASE_ARCHIVES.getValue());
            list1.add(map == null ? 0L : map.get("1"));
            list2.add(map == null ? 0L : map.get("2"));
        }
        lists.add(list1);
        lists.add(list2);
        ArchDiseaseArchivesAppraisalDataVo archArchivesBorrowStatisticsDataVo = new ArchDiseaseArchivesAppraisalDataVo();
        archArchivesBorrowStatisticsDataVo.setNames(archivesLibraries.stream().map(SysArchivesLibrary::getName).collect(Collectors.toList()));
        archArchivesBorrowStatisticsDataVo.setValues(lists);
        return archArchivesBorrowStatisticsDataVo;
    }
}
