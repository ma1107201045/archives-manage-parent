package com.yintu.rixing.archives;

import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.dto.archives.ArchivesStatsQueryDto;
import com.yintu.rixing.vo.archives.ArchCommonVo;
import com.yintu.rixing.vo.archives.ArchUsingPurposeStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchivesCommonVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/19 11:27:50
 * @Version: 1.0
 */
public interface IArchUsingPurposeStatisticsService {

    /**
     * 查询条件中每个档案库的名称
     *
     * @return 名称集
     */
    List<ArchCommonVo> findArchivesName();

    /**
     * 查询每个利用目的每个档案库的统计个数
     *
     * @param archCommonQueryDto 入参
     * @return 统计值集
     */
    ArchUsingPurposeStatisticsDataVo findArchUsingPurposeStatisticsData(ArchCommonQueryDto archCommonQueryDto);

    /**
     * 查询档案利用统计数据
     * @param archivesStatsQueryDto
     * @return
     */
    List<ArchivesCommonVo> findArchivesInfo(ArchivesStatsQueryDto archivesStatsQueryDto);

    /**
     * 导出档案利用统计数据
     * @param response
     * @param archivesStatsQueryDto
     */
    void exportExcel(HttpServletResponse response, ArchivesStatsQueryDto archivesStatsQueryDto);


}
