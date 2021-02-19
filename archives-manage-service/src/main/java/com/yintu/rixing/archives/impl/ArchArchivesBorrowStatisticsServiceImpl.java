package com.yintu.rixing.archives.impl;

import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.ArchArchivesBorrowStatisticsMapper;
import com.yintu.rixing.archives.IArchArchivesBorrowStatisticsService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.archives.ArchArchivesBorrowStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchArchivesQuantityStatisticsDataVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/2/19 17:11:43
 * @Version: 1.0
 */
public class ArchArchivesBorrowStatisticsServiceImpl extends ArchAbstractService implements IArchArchivesBorrowStatisticsService {

    @Autowired
    private ArchArchivesBorrowStatisticsMapper archArchivesBorrowStatisticsMapper;


    @Override
    public ArchArchivesBorrowStatisticsDataVo findArchivesBorrowStatisticsData(ArchCommonQueryDto archCommonQueryDto) {
        Date startDate = archCommonQueryDto.getStartDate();
        Date endDate = archCommonQueryDto.getEndDate();
        List<Integer> archivesIds = archCommonQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<List<Long>> lists = new ArrayList<>();
        List<Long> list1 = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        List<Long> list3 = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            String dataKey = archivesLibrary.getDataKey();
            List<Map<String, Object>> maps = archArchivesBorrowStatisticsMapper.selectArchivesBorrowStatisticsData(TableNameUtil.getFullTableName(dataKey), startDate, endDate);
            Long count1 = null;
            Long count2 = null;
            Long count3 = null;
            for (Map<String, Object> map : maps) {
                Integer status = (Integer) map.get(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
                if (status.equals(EnumArchivesOrder.TEMPORARY_LIBRARY.getValue().intValue())) {
                    count1 = (Long) map.get("count");
                } else if (status.equals(EnumArchivesOrder.SORTING_LIBRARY.getValue().intValue())) {
                    count2 = (Long) map.get("count");
                } else if (status.equals(EnumArchivesOrder.FORMAL_LIBRARY.getValue().intValue())) {
                    count3 = (Long) map.get("count");
                }
            }
            list1.add(count1 == null ? 0L : count1);
            list2.add(count2 == null ? 0L : count2);
            list3.add(count3 == null ? 0L : count3);
        }
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        ArchArchivesBorrowStatisticsDataVo archArchivesBorrowStatisticsDataVo = new ArchArchivesBorrowStatisticsDataVo();
        archArchivesBorrowStatisticsDataVo.setNames(archivesLibraries.stream().map(SysArchivesLibrary::getName).collect(Collectors.toList()));
        archArchivesBorrowStatisticsDataVo.setValues(lists);
        return archArchivesBorrowStatisticsDataVo;
    }
}