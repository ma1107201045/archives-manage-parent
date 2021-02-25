package com.yintu.rixing.archives;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/2/19 15:12:53
 * @Version: 1.0
 */
@Mapper
public interface ArchUsingPurposeStatisticsMapper {


    /**
     * 查询利用目的统计数据
     *
     * @param archivesLibraryId 档案库id
     * @param tableName         表名
     * @param borrowType        借阅类型 1.电子档案 2.实体档案
     * @param departmentId      部门id
     * @param startDate         开始日期
     * @param endDate           结束日期
     * @return ..
     */
    List<Map<String, Object>> selectArchUsingPurposeStatisticsData(Integer archivesLibraryId, String tableName, Short borrowType, Integer departmentId, Date startDate, Date endDate);
}
