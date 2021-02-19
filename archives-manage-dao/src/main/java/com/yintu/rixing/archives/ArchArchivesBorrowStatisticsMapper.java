package com.yintu.rixing.archives;

import org.apache.ibatis.annotations.Mapper;

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
     * @param tableName 表名
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return ..
     */
    List<Map<String, Object>> selectArchivesBorrowStatisticsData(String tableName, Date startDate, Date endDate);
}
