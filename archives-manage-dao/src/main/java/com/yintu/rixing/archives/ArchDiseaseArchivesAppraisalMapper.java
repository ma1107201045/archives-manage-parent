package com.yintu.rixing.archives;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/2/22 10:38:14
 * @Version: 1.0
 */
@Mapper
public interface ArchDiseaseArchivesAppraisalMapper {

    /**
     * 查询档案统计数量数据
     *
     * @param archivesLibraryId 档案库id
     * @param tableName         表名
     * @param departmentId      部门id
     * @param startDate         开始日期
     * @param endDate           结束日期
     * @param status            档案状态：0.档案收集 1.临时库 2.整理库 3.正式库 4.回收站 5.销毁库 6.回退管理 7.病档管理 8.档案销毁
     * @return ..
     */
    Map<String, Long> selectArchDiseaseArchivesAppraisalData(Integer archivesLibraryId, String tableName, Integer departmentId, Date startDate, Date endDate, Short status);

    /**
     * 查询档案统计数量数据
     *
     * @param archivesLibraryId 档案库id
     * @param tableName         表名
     * @param startDate         开始日期
     * @param endDate           结束日期
     * @return ..
     */
    Map<String, Long> selectDiseaseArchivesAppraisalData(Integer archivesLibraryId, String tableName,  Date startDate, Date endDate);
}
