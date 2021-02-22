package com.yintu.rixing.archives.impl;

import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.IArchYearReportService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.vo.archives.ArchYearReportDataVo;
import org.springframework.stereotype.Service;

/**
 * @Author: mlf
 * @Date: 2021/2/22 14:39:06
 * @Version: 1.0
 */
@Service
public class ArchYearReportServiceImpl extends ArchAbstractService implements IArchYearReportService {

    @Override
    public ArchYearReportDataVo findYearReportData(ArchCommonQueryDto archCommonQueryDto) {
        return null;
    }
}
