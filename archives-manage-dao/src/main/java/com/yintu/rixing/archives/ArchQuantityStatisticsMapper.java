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
public interface ArchQuantityStatisticsMapper {

    List<Map<String, Object>> findArchivesData(String tableName, Date startDate, Date endDate);
}
