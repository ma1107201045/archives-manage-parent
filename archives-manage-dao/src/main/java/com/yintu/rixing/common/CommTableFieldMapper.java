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

    void createTable(String tableName, List<CommTableField> commTableFields);

    void dropByTableName(String tableName);

    void alterByTableName(String oldTableName, String newTableName);

    void descByTableName(String tableName);
}
