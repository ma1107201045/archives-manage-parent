package com.yintu.rixing.common;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/11 18:25:24
 * @Version: 1.0
 */
@Mapper
public interface CommTableFieldMapper {

    void createTable(String tableName, String tableComment, List<CommTableField> commTableFields);

    void dropTableByTableName(String tableName);

    void alterTableNameByTableName(String oldTableName, String newTableName);

    void alterTableCommentByTableName(String tableName, String tableComment);

    List<CommTableField> showByTableName(String tableName);

    long countDataByTableName(String tableName);
}
