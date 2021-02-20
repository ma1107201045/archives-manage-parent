package com.yintu.rixing.archives.impl;

import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.ArchArchivesQuantityStatisticsMapper;
import com.yintu.rixing.archives.ArchSearchArchivesQuantityStatisticsMapper;
import com.yintu.rixing.archives.IArchSearchArchivesQuantityStatisticsService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.archives.ArchArchivesQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchSearchArchivesQuantityStatisticsDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/2/20 15:09:44
 * @Version: 1.0
 */
@Service
public class ArchSearchArchivesQuantityStatisticsServiceImpl extends ArchAbstractService implements IArchSearchArchivesQuantityStatisticsService {

    @Autowired
    private ArchSearchArchivesQuantityStatisticsMapper archSearchArchivesQuantityStatisticsMapper;

    @Override
    public ArchSearchArchivesQuantityStatisticsDataVo findSearchArchivesQuantityStatisticsData(ArchCommonQueryDto archCommonQueryDto) {
        Integer departmentId = archCommonQueryDto.getDepartmentId();
        Date startDate = archCommonQueryDto.getStartDate();
        Date endDate = archCommonQueryDto.getEndDate();
        List<Integer> archivesIds = archCommonQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<Long> values = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            Integer id = archivesLibrary.getId();
            String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
            Long count = archSearchArchivesQuantityStatisticsMapper.selectSearchArchivesQuantityStatisticsData(id, tableName, departmentId, startDate, endDate);
            values.add(count);
        }
        ArchSearchArchivesQuantityStatisticsDataVo archArchivesQuantityStatisticsDataVo = new ArchSearchArchivesQuantityStatisticsDataVo();
        archArchivesQuantityStatisticsDataVo.setNames(archivesLibraries.stream().map(SysArchivesLibrary::getName).collect(Collectors.toList()));
        archArchivesQuantityStatisticsDataVo.setValues(values);
        return archArchivesQuantityStatisticsDataVo;
    }
}
