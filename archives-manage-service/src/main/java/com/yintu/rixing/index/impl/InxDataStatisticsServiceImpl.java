package com.yintu.rixing.index.impl;

import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.index.IInxDataStatisticsService;
import com.yintu.rixing.index.InxDataStatisticsMapper;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.util.TableNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/2/24 16:57:34
 * @Version: 1.0
 */
@Service
public class InxDataStatisticsServiceImpl implements IInxDataStatisticsService {
    @Autowired
    private InxDataStatisticsMapper inxDataStatisticsDao;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

    @Override
    public List<Long> findInxDataStatistics() {
        //求出每个动态表的的表名
        List<String> tableNames = iSysArchivesLibraryService.listByType((short) 2).stream().map(sysArchivesLibrary -> TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey())).collect(Collectors.toList());
        return inxDataStatisticsDao.selectInxDataStatistics((short) 1, EnumFlag.True.getValue(), tableNames, EnumArchivesOrder.FORMAL_LIBRARY.getValue());
    }
}
