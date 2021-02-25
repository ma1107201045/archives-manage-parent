package com.yintu.rixing.index;

import com.yintu.rixing.dto.index.InxUsingPurposeStatisticsQueryDto;
import com.yintu.rixing.vo.index.InxSearchArchivesQuantityStatisticsVo;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/25 14:19:22
 * @Version: 1.0
 */
public interface IInxSearchArchivesQuantityAndUsingPurposeStatisticsService {


    /**
     * 查档数量统计(最近一年数据)
     *
     * @return ..
     */
    InxSearchArchivesQuantityStatisticsVo findSearchArchivesQuantityStatisticsData();

    /**
     * 利用目的统计
     *
     * @param inxUsingPurposeStatisticsQueryDto ..
     * @return ..
     */
    List<Long> findUsingPurposeData(InxUsingPurposeStatisticsQueryDto inxUsingPurposeStatisticsQueryDto);
}
