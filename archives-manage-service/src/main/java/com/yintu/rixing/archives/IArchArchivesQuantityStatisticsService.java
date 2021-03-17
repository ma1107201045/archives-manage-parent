package com.yintu.rixing.archives;

import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.dto.archives.ArchivesStatsQueryDto;
import com.yintu.rixing.vo.archives.ArchArchivesQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchCommonVo;
import com.yintu.rixing.vo.archives.ArchivesCommonVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/30 14:21:55
 * @Version: 1.0
 */
public interface IArchArchivesQuantityStatisticsService {


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
    ArchArchivesQuantityStatisticsDataVo findArchArchivesQuantityStatisticsData(ArchCommonQueryDto archCommonQueryDto);

    /**
     * 查询条件中每个档案库的信息
     *
     * @return 信息
     * @param archivesStatsQueryDto
     */
    List<ArchivesCommonVo> findArchivesInfo(ArchivesStatsQueryDto archivesStatsQueryDto);

    /**
     * 导出表格数据
     * @param response
     * @param archivesStatsQueryDto
     */
    void exportExcel(HttpServletResponse response, ArchivesStatsQueryDto archivesStatsQueryDto) throws IOException;
}
