package com.yintu.rixing.archives;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/30 15:38:31
 * @Version: 1.0
 */
@Mapper
public interface ArchArchivesQuantityStatisticsMapper {

    /**
     * 查询档案统计数量数据
     *
     * @param tableName         表名
     * @param archivesLibraryId 档案库id
     * @param departmentId      部门id
     * @param startDate         开始日期
     * @param endDate           结束日期
     * @return ..
     */
    Map<String, Long> selectArchivesQuantityStatisticsData(Integer archivesLibraryId, String tableName, Integer departmentId, Date startDate, Date endDate);
}
