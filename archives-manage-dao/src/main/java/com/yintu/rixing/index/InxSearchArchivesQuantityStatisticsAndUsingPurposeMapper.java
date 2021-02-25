package com.yintu.rixing.index;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/2/25 15:33:18
 * @Version: 1.0
 */
@Mapper
public interface InxSearchArchivesQuantityStatisticsAndUsingPurposeMapper {


    /**
     * 查档数量统计(最近一年数据)
     *
     * @param archivesLibraryId 档案库id
     * @param searchType        搜索类型
     * @param year              年份
     * @return ..
     */
    Map<String, Long> selectSearchArchivesQuantityStatisticsData(Integer archivesLibraryId, Short searchType, Integer year);

    /**
     * 利用目的统计
     *
     * @param borrowType 借阅类型 1.电子档案 2.实体档案
     * @param date       所选日期
     * @param makeId     所选日期
     * @return ..
     */
    Map<String, Long> selectUsingPurposeData(Short borrowType, Date date, Integer makeId);

}
