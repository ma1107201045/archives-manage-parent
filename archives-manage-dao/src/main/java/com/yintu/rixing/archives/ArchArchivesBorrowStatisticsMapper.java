package com.yintu.rixing.archives;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/2/19 17:13:01
 * @Version: 1.0
 */
@Mapper
public interface ArchArchivesBorrowStatisticsMapper {


    /**
     * 查询档案借阅统计数据
     *
     * @param archivesLibraryId 档案库id
     * @param tableName         表名
     * @param departmentId      部门id
     * @param borrowType        借阅类型 1.实体档案 2.电子档案
     * @param startDate         开始日期
     * @param endDate           结束日期
     * @return ..
     */
    Map<String, BigDecimal> selectArchivesBorrowStatisticsData(Integer archivesLibraryId, String tableName, Short borrowType, Integer departmentId, Date startDate, Date endDate);
}
