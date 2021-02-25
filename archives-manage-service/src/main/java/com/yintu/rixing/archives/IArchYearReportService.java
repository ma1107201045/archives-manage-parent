package com.yintu.rixing.archives;

import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.vo.archives.ArchCommonVo;
import com.yintu.rixing.vo.archives.ArchYearReportDataVo;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/22 14:38:24
 * @Version: 1.0
 */
public interface IArchYearReportService {


    /**
     * 查询条件中每个档案库的名称
     *
     * @return 名称集
     */
    List<ArchCommonVo> findArchivesName();

    /**
     * 年报
     *
     * @param archCommonQueryDto 入参
     * @return 统计值集
     */
    ArchYearReportDataVo findArchYearReportData(ArchCommonQueryDto archCommonQueryDto);
}
