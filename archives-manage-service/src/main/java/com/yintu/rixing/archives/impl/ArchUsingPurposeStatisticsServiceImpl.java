package com.yintu.rixing.archives.impl;

import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.IArchUsingPurposeStatisticsService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.vo.archives.ArchArchivesQuantityStatisticsDataVo;
import org.springframework.stereotype.Service;

/**
 * @Author: mlf
 * @Date: 2021/2/19 11:29:01
 * @Version: 1.0
 */
@Service
public class ArchUsingPurposeStatisticsServiceImpl extends ArchAbstractService implements IArchUsingPurposeStatisticsService {

    @Override
    public ArchArchivesQuantityStatisticsDataVo findUsingPurposeData(ArchCommonQueryDto archCommonQueryDto) {
        return null;
    }
}
