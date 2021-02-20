package com.yintu.rixing.archives;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2021/2/20 15:16:25
 * @Version: 1.0
 */
@Mapper
public interface ArchSearchArchivesQuantityStatisticsMapper {


    /**
     * 查询档案统计数量数据
     *
     * @param archivesLibraryId 档案库id
     * @param tableName         表名
     * @param departmentId      部门id
     * @param startDate         开始日期
     * @param endDate           结束日期
     * @return ..
     */
    Long selectSearchArchivesQuantityStatisticsData(Integer archivesLibraryId, String tableName, Integer departmentId, Date startDate, Date endDate);
}
