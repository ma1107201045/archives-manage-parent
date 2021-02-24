package com.yintu.rixing.index.impl;

import com.yintu.rixing.index.IInxDataStatisticsService;
import com.yintu.rixing.index.InxDataStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mlf
 * @Date: 2021/2/24 16:57:34
 * @Version: 1.0
 */
@Service
public class InxDataStatisticsServiceImpl implements IInxDataStatisticsService {
    @Autowired
    private InxDataStatisticsMapper inxDataStatisticsDao;

}
