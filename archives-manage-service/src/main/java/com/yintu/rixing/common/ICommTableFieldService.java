package com.yintu.rixing.common;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/11 18:09:52
 * @Version: 1.0
 */
public interface ICommTableFieldService {

    @Transactional(rollbackFor = {Exception.class})
    void addTable(String tableName, String tableComment, List<CommTableField> commTableFields);

    @Transactional(rollbackFor = {Exception.class})
    void removeTableByTableName(String tableName);

    @Transactional(rollbackFor = {Exception.class})
    void editTableNameByTableName(String oldTableName, String newTableName);

    @Transactional(rollbackFor = {Exception.class})
    void editTableCommentByTableName(String tableName, String tableComment);

    long countDataByTableName(String tableName);

    @Transactional(rollbackFor = {Exception.class})
    void add(String tableName, CommTableField commTableField);

    @Transactional(rollbackFor = {Exception.class})
    void addIndex(String tableName, String fieldName);

    @Transactional(rollbackFor = {Exception.class})
    void drop(String tableName, String fieldName);

    @Transactional(rollbackFor = {Exception.class})
    void dropByFieldNames(String tableName, Set<String> fieldNames);

    @Transactional(rollbackFor = {Exception.class})
    void dropIndex(String tableName, String fieldName);

    @Transactional(rollbackFor = {Exception.class})
    void alter(String tableName, String oldTableName, CommTableField commTableField);

    List<CommTableField> findByTableName(String tableName);
}
