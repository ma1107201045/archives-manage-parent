package com.yintu.rixing.archives;

import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.dto.archives.ArchivesStatsQueryDto;
import com.yintu.rixing.vo.archives.ArchCommonVo;
import com.yintu.rixing.vo.archives.ArchSearchArchivesQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchivesCommonVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/20 15:09:08
 * @Version: 1.0
 */
public interface IArchSearchArchivesQuantityStatisticsService {

    /**
     * 查询条件中每个档案库的名称
     *
     * @return 名称集
     */
    List<ArchCommonVo> findArchivesName();

    /**
     * 查询每个档案库文件的查询次数值
     *
     * @param archCommonQueryDto 入参
     * @return 统计值集
     */
    ArchSearchArchivesQuantityStatisticsDataVo findArchSearchArchivesQuantityStatisticsData(ArchCommonQueryDto archCommonQueryDto);

    /**
     * 查询条件中每个档案库的信息
     *
     * @return 信息
     * @param archivesStatsQueryDto
     */
    List<ArchivesCommonVo> findArchivesInfo(ArchivesStatsQueryDto archivesStatsQueryDto);


    /**
     * 查询条件中每个档案库的信息
     *
     * @return 信息
     * @param archivesStatsQueryDto
     */
    List<ArchivesCommonVo> findArchivesInfos(ArchivesStatsQueryDto archivesStatsQueryDto);

    /**
     * 导出表格数据
     * @param response
     * @param archivesStatsQueryDto
     */
    void exportExcel(HttpServletResponse response, ArchivesStatsQueryDto archivesStatsQueryDto) throws IOException;
}
