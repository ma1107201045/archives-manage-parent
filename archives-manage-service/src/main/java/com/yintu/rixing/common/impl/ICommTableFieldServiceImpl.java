package com.yintu.rixing.common.impl;

import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.CommTableFieldMapper;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.ISysTemplateLibraryFieldTypeService;
import com.yintu.rixing.system.SysArchivesLibraryField;
import com.yintu.rixing.system.SysTemplateLibraryFieldType;
import com.yintu.rixing.util.TableNameUtil;
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
    @Autowired
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;

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

    @Override
    public void dropIndex(String tableName, String fieldName) {
        commTableFieldMapper.dropIndex(tableName, fieldName);
    }

    @Override
    public void alter(String tableName, String oldFieldName, CommTableField commTableField) {
        commTableFieldMapper.alter(tableName, oldFieldName, commTableField);
    }

    @Override
    public void alterOrder(String tableName, CommTableField commTableField, String fieldName) {
        commTableFieldMapper.alterOrder(tableName, commTableField, fieldName);
    }

    @Override
    public CommTableField findByDataKeyAndSysArchivesLibraryField(String dataKey, SysArchivesLibraryField sysArchivesLibraryField) {
        String tableName = TableNameUtil.getFullTableName(dataKey);
        Integer templateLibraryFieldTypeId = sysArchivesLibraryField.getTemplateLibraryFieldTypeId();
        SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
        String dataType = sysTemplateLibraryFieldType.getDataKey();
        CommTableField commTableField = new CommTableField();
        commTableField.setTableName(tableName);
        commTableField.setFieldName(dataKey);
        commTableField.setDataType(dataType);
        commTableField.setLength(sysArchivesLibraryField.getLength());
        if ("datetime".equals(dataType))
            commTableField.setLength(6);
        commTableField.setIsNull(sysArchivesLibraryField.getRequired() == 1 ? (short) 0 : (short) 1);
        commTableField.setIsIndex(sysArchivesLibraryField.getIndex());
        commTableField.setComment(sysArchivesLibraryField.getName());
        return commTableField;
    }


}
