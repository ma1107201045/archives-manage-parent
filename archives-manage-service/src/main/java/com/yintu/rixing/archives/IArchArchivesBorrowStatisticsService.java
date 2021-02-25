package com.yintu.rixing.archives;

import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.vo.archives.ArchArchivesBorrowStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchCommonVo;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/19 17:10:17
 * @Version: 1.0
 */
public interface IArchArchivesBorrowStatisticsService {

    /**
     * 查询条件中每个档案库的名称
     *
     * @return 名称集
     */
    List<ArchCommonVo> findArchivesName();

    /**
     * 查询每个流程中每个档案库的统计个数
     *
     * @param archCommonQueryDto 入参
     * @return 统计值集
     */
    ArchArchivesBorrowStatisticsDataVo findArchArchivesBorrowStatisticsData(ArchCommonQueryDto archCommonQueryDto);
}
