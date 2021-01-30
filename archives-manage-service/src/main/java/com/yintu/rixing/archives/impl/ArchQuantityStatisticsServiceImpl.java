package com.yintu.rixing.archives.impl;

import com.yintu.rixing.archives.ArchQuantityStatisticsMapper;
import com.yintu.rixing.archives.IArchQuantityStatisticsService;
import com.yintu.rixing.dto.archives.ArchQuantityStatisticsQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.archives.ArchQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchQuantityStatisticsQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ArchQuantityStatisticsServiceImpl implements IArchQuantityStatisticsService {
    @Autowired
    private ArchQuantityStatisticsMapper archQuantityStatisticsMapper;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

    @Override
    public List<ArchQuantityStatisticsQueryVo> findArchivesName() {
        List<SysArchivesLibrary> sysArchivesLibraries = iSysArchivesLibraryService.listByType((short) 2);
        List<ArchQuantityStatisticsQueryVo> archQuantityStatisticsQueryVos = new ArrayList<>();
        for (SysArchivesLibrary sysArchivesLibrary : sysArchivesLibraries) {
            ArchQuantityStatisticsQueryVo archQuantityStatisticsQueryVo = new ArchQuantityStatisticsQueryVo();
            archQuantityStatisticsQueryVo.setArchivesId(sysArchivesLibrary.getId());
            archQuantityStatisticsQueryVo.setArchivesName(sysArchivesLibrary.getName());
            archQuantityStatisticsQueryVos.add(archQuantityStatisticsQueryVo);
        }
        return archQuantityStatisticsQueryVos;
    }

    @Override
    public ArchQuantityStatisticsDataVo findArchivesData(ArchQuantityStatisticsQueryDto archQuantityStatisticsQueryDto) {
        Date startDate = archQuantityStatisticsQueryDto.getStartDate();
        Date endDate = archQuantityStatisticsQueryDto.getEndDate();
        List<Integer> archivesIds = archQuantityStatisticsQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        ArchQuantityStatisticsDataVo archQuantityStatisticsDataVo = new ArchQuantityStatisticsDataVo();
        List<String> archivesLibraryNames = archivesLibraries.stream().map(SysArchivesLibrary::getName).collect(Collectors.toList());
        List<List<Long>> lists = new ArrayList<>();
        List<Long> list1 = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        List<Long> list3 = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            String dataKey = archivesLibrary.getDataKey();
            List<Map<String, Object>> maps = archQuantityStatisticsMapper.findArchivesData(TableNameUtil.getFullTableName(dataKey), startDate, endDate);
            Long count1 = null;
            Long count2 = null;
            Long count3 = null;
            for (Map<String, Object> map : maps) {
                String countName = "count";
                Integer status = (Integer) map.get(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
                if (status.equals(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue().intValue())) {
                    count1 = (Long) map.get(countName);
                } else if (status.equals(EnumArchivesOrder.SORTING_LIBRARY.getValue().intValue())) {
                    count2 = (Long) map.get(countName);
                } else if (status.equals(EnumArchivesOrder.FORMAL_LIBRARY.getValue().intValue())) {
                    count3 = (Long) map.get(countName);
                }
            }
            if (count1 == null)
                list1.add(0L);
            else list1.add(count1);
            if (count2 == null)
                list2.add(0L);
            else list2.add(count2);
            if (count3 == null)
                list3.add(0L);
            else list3.add(count3);
        }
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        archQuantityStatisticsDataVo.setNames(archivesLibraryNames);
        archQuantityStatisticsDataVo.setValues(lists);
        return archQuantityStatisticsDataVo;
    }


}
