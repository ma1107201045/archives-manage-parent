package com.yintu.rixing.common;

import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.SysArchivesLibraryField;

import java.util.List;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/11 18:09:52
 * @Version: 1.0
 */
public interface ICommTableFieldService {


    void addTable(String tableName, String tableComment, List<CommTableField> commTableFields);


    void removeTableByTableName(String tableName);


    void editTableNameByTableName(String oldTableName, String newTableName);


    void editTableCommentByTableName(String tableName, String tableComment);

    List<CommTableField> findByTableName(String tableName);

    long countDataByTableName(String tableName);


    void add(String tableName, CommTableField commTableField);

    void addIndex(String tableName, String fieldName);


    void drop(String tableName, String fieldName);

    void dropByFieldNames(String tableName, Set<String> fieldNames);

    void dropIndex(String tableName, String fieldName);


    void alter(String tableName, String oldTableName, CommTableField commTableField);

    void alterOrder(String tableName, CommTableField commTableField, String fieldName);


    void isHasDataByTableName(String tableName);

    CommTableField findByDataKeyAndSysArchivesLibraryField(String dataKey, SysArchivesLibraryField sysArchivesLibraryField);


}
