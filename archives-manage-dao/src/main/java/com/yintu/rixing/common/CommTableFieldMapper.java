package com.yintu.rixing.common;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/11 18:25:24
 * @Version: 1.0
 */
@Mapper
public interface CommTableFieldMapper {
    //对表本身操作
    void createTable(String tableName, String tableComment, List<CommTableField> commTableFields);

    void dropTableByTableName(String tableName);

    void alterTableNameByTableName(String oldTableName, String newTableName);

    void alterTableCommentByTableName(String tableName, String tableComment);

    long countDataByTableName(String tableName);

    //对表字段索引操作
    void create(String tableName, CommTableField commTableField);

    void createIndex(String tableName, String fieldName);

    void drop(String tableName, String fieldName);

    void dropByFields(String tableName, Set<String> fieldNames);

    void dropIndex(String tableName, String fieldName);

    void alter(String tableName, String oldFieldName, CommTableField commTableField);

    List<CommTableField> showByTableName(String tableName);

}
