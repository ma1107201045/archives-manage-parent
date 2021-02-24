package com.yintu.rixing.archives.impl;

import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.ArchYearReportMapper;
import com.yintu.rixing.archives.IArchYearReportService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.archives.ArchSearchArchivesQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchYearReportDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/2/22 14:39:06
 * @Version: 1.0
 */
@Service
public class ArchYearReportServiceImpl extends ArchAbstractService implements IArchYearReportService {

    @Autowired
    private ArchYearReportMapper archYearReportMapper;

    @Override
    public ArchYearReportDataVo findYearReportData(ArchCommonQueryDto archCommonQueryDto) {
        Integer departmentId = archCommonQueryDto.getDepartmentId();
        Integer year = archCommonQueryDto.getYear();
        List<Integer> archivesIds = archCommonQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<List<Long>> values = new ArrayList<>();
        List<Long> list1 = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        List<Long> list3 = new ArrayList<>();
        List<Long> list4 = new ArrayList<>();
        List<Long> list5 = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            Integer id = archivesLibrary.getId();
            String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
            List<Long> counts = archYearReportMapper.selectYearReportData(id, tableName, (short) 1, (short) 1, Arrays.asList((short) 1, (short) 2, (short) 3), EnumArchivesOrder.DISEASE_ARCHIVES.getValue(), departmentId, year);
            list1.add(counts.get(0));
            list2.add(counts.get(1));
            list3.add(counts.get(2));
            list4.add(counts.get(3));
            list5.add(counts.get(4));
        }
        values.add(list1);
        values.add(list2);
        values.add(list3);
        values.add(list4);
        values.add(list5);
        ArchYearReportDataVo archYearReportDataVo = new ArchYearReportDataVo();
        archYearReportDataVo.setNames(archivesLibraries.stream().map(SysArchivesLibrary::getName).collect(Collectors.toList()));
        archYearReportDataVo.setValues(values);
        return archYearReportDataVo;
    }
}
