package com.yintu.rixing.common.impl;

import cn.hutool.core.util.StrUtil;
import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.CommTableFieldMapper;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.ISysDataTypeService;
import com.yintu.rixing.system.SysArchivesLibraryField;
import com.yintu.rixing.system.SysDataType;
import com.yintu.rixing.util.TableNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/12 10:15:42
 * @Version: 1.0
 */
@Service
public class CommTableFieldServiceImpl implements ICommTableFieldService {
    @Autowired
    private CommTableFieldMapper commTableFieldMapper;
    @Autowired
    private ISysDataTypeService iSysTemplateLibraryFieldTypeService;

    public void addTable(String tableName, String tableComment, List<CommTableField> commTableFields) {
        commTableFieldMapper.createTable(tableName, tableComment, commTableFields);
    }

    @Override
    public void removeTableByTableName(String tableName) {
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
    public CommTableField findFixed() {
        CommTableField commTableField = new CommTableField();
        commTableField.setFieldName("data_id");
        commTableField.setDataType("int");
        commTableField.setLength(11);
        commTableField.setIsNull((short) 0);
        commTableField.setIsIndex((short) 1);
        commTableField.setComment("动态表id");
        return commTableField;
    }


    @Override
    public List<CommTableField> findByTableName(String tableName) {
        List<Map<String, String>> maps = commTableFieldMapper.showByTableName(tableName);
        List<CommTableField> commTableFields = new ArrayList<>();
        for (Map<String, String> map : maps) {
            CommTableField commTableField = new CommTableField();
            commTableField.setTableName(tableName);
            commTableField.setFieldName(map.get("Field"));
            String type = map.get("Type");
            List<String> list = StrUtil.split(type, '(');
            if (!list.isEmpty()) {
                commTableField.setDataType(list.get(0));
                if (list.size() == 1) {
                    commTableField.setLength(0);
                } else {
                    String length = list.get(1);
                    commTableField.setLength(Integer.valueOf(length.substring(0, length.length() - 1)));
                }
            }
            commTableField.setIsIndex("MUL".equals(map.get("Key")) ? (short) 1 : (short) 0);
            commTableField.setIsNull("YES".equals(map.get("Null")) ? (short) 1 : (short) 0);
            commTableField.setComment(map.get("Comment"));
            commTableFields.add(commTableField);
        }
        return commTableFields;
    }

    @Override
    public void isHasDataByTableName(String tableName) {
        if (this.countDataByTableName(tableName) > 0) {
            throw new BaseRuntimeException("档案库中有数据，操作失败");
        }
    }

    @Override
    public CommTableField findByDataKeyAndSysArchivesLibraryField(String dataKey, SysArchivesLibraryField sysArchivesLibraryField) {
        String tableName = TableNameUtil.getFullTableName(dataKey);
        Integer templateLibraryFieldTypeId = sysArchivesLibraryField.getTemplateLibraryFieldTypeId();
        SysDataType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
        String dataType = sysTemplateLibraryFieldType.getDataKey();
        CommTableField commTableField = new CommTableField();
        commTableField.setTableName(tableName);
        commTableField.setFieldName(sysArchivesLibraryField.getDataKey());
        commTableField.setDataType(dataType);
        commTableField.setLength(sysArchivesLibraryField.getLength());
        commTableField.setIsNull(sysArchivesLibraryField.getRequired() == 1 ? (short) 0 : (short) 1);
        commTableField.setIsIndex(sysArchivesLibraryField.getIndex());
        commTableField.setComment(sysArchivesLibraryField.getName());
        return commTableField;
    }


}
