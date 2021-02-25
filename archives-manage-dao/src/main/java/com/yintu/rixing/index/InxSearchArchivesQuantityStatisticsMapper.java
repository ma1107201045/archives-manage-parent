package com.yintu.rixing.index;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: mlf
 * @Date: 2021/2/25 15:33:18
 * @Version: 1.0
 */
@Mapper
public interface InxSearchArchivesQuantityStatisticsMapper {


    /**
     * 查档数量统计(最近一年数据)
     *
     * @param archivesLibraryId 档案库id
     * @param tableName         表名
     * @param searchType        搜索类型
     * @return ..
     */
    Long selectSearchArchivesQuantityStatisticsData(Integer archivesLibraryId, String tableName, Short searchType);


}
