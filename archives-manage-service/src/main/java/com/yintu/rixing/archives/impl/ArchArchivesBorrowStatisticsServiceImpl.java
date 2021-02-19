package com.yintu.rixing.archives.impl;

import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.ArchArchivesBorrowStatisticsMapper;
import com.yintu.rixing.archives.IArchUsingPurposeStatisticsService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.vo.archives.ArchUsingPurposeStatisticsDataVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: mlf
 * @Date: 2021/2/19 17:11:43
 * @Version: 1.0
 */
public class ArchArchivesBorrowStatisticsServiceImpl extends ArchAbstractService implements IArchUsingPurposeStatisticsService {

    @Autowired
    private ArchArchivesBorrowStatisticsMapper archArchivesBorrowStatisticsMapper;


    @Override
    public ArchUsingPurposeStatisticsDataVo findUsingPurposeData(ArchCommonQueryDto archCommonQueryDto) {
        return null;
    }
}
