package com.yintu.rixing.common.impl;

import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.CommTableFieldMapper;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.exception.BaseRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/12 10:15:42
 * @Version: 1.0
 */
@Service
public class ICommTableFieldServiceImpl implements ICommTableFieldService {
    @Autowired
    private CommTableFieldMapper commTableFieldMapper;


    public void addTable(String tableName, String tableComment, List<CommTableField> commTableFields) {
        commTableFieldMapper.createTable(tableName, tableComment, commTableFields);
    }

    @Override
    public void removeTableByTableName(String tableName) {
        if (this.countDataByTableName(tableName) > 0)
            throw new BaseRuntimeException("表中有数据，删除失败");
        commTableFieldMapper.dropTableByTableName(tableName);
    }

    @Override
    public void editTableNameByTableName(String oldTableName, String newTableName) {
        commTableFieldMapper.alterTableNameByTableName(oldTableName, newTableName);
    }

    @Override
    public void editTableCommentByTableName(String tableName, String tableComment) {
        commTableFieldMapper.alterTableCommentByTableName(tableName, tableComment);
    }

    @Override
    public List<CommTableField> findByTableName(String tableName) {
        return commTableFieldMapper.showByTableName(tableName);
    }

    @Override
    public long countDataByTableName(String tableName) {
        return commTableFieldMapper.countDataByTableName(tableName);
    }

    @Override
    public void add(String tableName, CommTableField commTableField) {
        commTableFieldMapper.create(tableName, commTableField);
    }

    @Override
    public void addIndex(String tableName, String fieldName) {
        commTableFieldMapper.createIndex(tableName, fieldName);
    }

    @Override
    public void drop(String tableName, String fieldName) {
        commTableFieldMapper.drop(tableName, fieldName);
    }

    @Override
    public void dropByFieldNames(String tableName, Set<String> fieldNames) {
        commTableFieldMapper.dropByFields(tableName, fieldNames);
    }


}
