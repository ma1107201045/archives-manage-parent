package com.yintu.rixing.index.impl;

import cn.hutool.core.date.DateUtil;
import com.yintu.rixing.dto.index.InxUsingPurposeStatisticsQueryDto;
import com.yintu.rixing.index.IInxSearchArchivesQuantityAndUsingPurposeStatisticsService;
import com.yintu.rixing.index.InxSearchArchivesQuantityStatisticsAndUsingPurposeMapper;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.vo.index.InxSearchArchivesQuantityStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/2/25 14:20:01
 * @Version: 1.0
 */
@Service
public class InxSearchArchivesQuantityStatisticsAndUsingPurposeServiceImpl implements IInxSearchArchivesQuantityAndUsingPurposeStatisticsService {

    @Autowired
    private InxSearchArchivesQuantityStatisticsAndUsingPurposeMapper inxSearchArchivesQuantityStatisticsAndUsingPurposeMapper;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

    @Override
    public InxSearchArchivesQuantityStatisticsVo findSearchArchivesQuantityStatisticsData() {
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByType((short) 2);
        List<List<Long>> lists = new ArrayList<>();
        List<Long> list1 = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        List<Long> list3 = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            Integer id = archivesLibrary.getId();
            Map<String, Long> map = inxSearchArchivesQuantityStatisticsAndUsingPurposeMapper.selectSearchArchivesQuantityStatisticsData(id, (short) 1, DateUtil.year(DateUtil.date()) - 1);
            list1.add(map == null ? 0L : map.get("1"));
            list2.add(map == null ? 0L : map.get("2"));
            list3.add(map == null ? 0L : map.get("3"));
        }
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        InxSearchArchivesQuantityStatisticsVo inxSearchArchivesQuantityStatisticsVo = new InxSearchArchivesQuantityStatisticsVo();
        inxSearchArchivesQuantityStatisticsVo.setNames(archivesLibraries.stream().map(SysArchivesLibrary::getName).collect(Collectors.toList()));
        inxSearchArchivesQuantityStatisticsVo.setValues(lists);
        return inxSearchArchivesQuantityStatisticsVo;
    }

    @Override
    public List<Long> findUsingPurposeData(InxUsingPurposeStatisticsQueryDto iInxUsingPurposeStatisticsQueryDto) {
        Date date = iInxUsingPurposeStatisticsQueryDto.getDate();
        Integer makeId = iInxUsingPurposeStatisticsQueryDto.getMakeId();
        Map<String, Long> map = inxSearchArchivesQuantityStatisticsAndUsingPurposeMapper.selectUsingPurposeData((short) 1, date, makeId);
        List<Long> list = new ArrayList<>();
        Long choose = map.get("choose") == null ? 0L : map.get("choose");
        Long total = map.get("total");
        list.add(choose);
        list.add(total - choose);
        return list;
    }
}
