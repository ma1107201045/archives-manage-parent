package com.yintu.rixing.archives;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/22 15:12:19
 * @Version: 1.0
 */
@Mapper
public interface ArchYearReportMapper {


    /**
     * 查询利用目的统计数据
     *
     * @param archivesLibraryId 档案库id
     * @param tableName         表名
     * @param borrowType        借阅类型 1.实体档案 2.电子档案
     * @param statusList        状态列表
     * @param status            状态
     * @param departmentId      部门id
     * @param year              开始日期
     * @return ..
     */
    List<Long> selectYearReportData(Integer archivesLibraryId, String tableName, Short borrowType, List<Short> statusList, Short status, Integer departmentId, Integer year);

}
