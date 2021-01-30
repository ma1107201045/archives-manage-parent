package com.yintu.rixing.archives;

import com.yintu.rixing.dto.archives.ArchQuantityStatisticsQueryDto;
import com.yintu.rixing.vo.archives.ArchQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchQuantityStatisticsQueryVo;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/30 14:21:55
 * @Version: 1.0
 */
public interface IArchQuantityStatisticsService {


    /**
     * 查询条件中每个档案库的名称
     *
     * @return 名称集
     */
    List<ArchQuantityStatisticsQueryVo> findArchivesName();

    /**
     * 查询每个流程中每个档案库的统计个数
     *
     * @return 统计值集
     */
    ArchQuantityStatisticsDataVo findArchivesData(ArchQuantityStatisticsQueryDto archQuantityStatisticsQueryDto);
}
