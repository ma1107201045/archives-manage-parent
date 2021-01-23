package com.yintu.rixing.warehouse.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.system.ISysTemplateLibraryFieldTypeService;
import com.yintu.rixing.system.SysTemplateLibraryFieldType;
import com.yintu.rixing.warehouse.IWareTemplateLibraryFieldService;
import com.yintu.rixing.warehouse.WareTemplateLibraryField;


import com.yintu.rixing.warehouse.WareTemplateLibraryFiledMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 库房管理实体库字段表 服务实现类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-15
 */
@Service
public class WareTemplateLibraryFieldServiceImpl extends ServiceImpl<WareTemplateLibraryFiledMapper, WareTemplateLibraryField> implements IWareTemplateLibraryFieldService {
    @Autowired
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;
    @Autowired
    private ICommTableFieldService iCommTableFieldService;
    @Autowired
    private WareTemplateLibraryFiledMapper wareTemplateLibraryFiledMapper;
    @Override
    public Integer findTable(String tableName) {
        return wareTemplateLibraryFiledMapper.findTable(tableName);
    }

    @Override
    public void creatTable(JSONObject jsonObject,Integer times) {
        String tableName ="ware_physical_warehouse";
        String tableComment="实体库表";
        JSONArray tableDates = jsonObject.getJSONArray("jsonObject");
        List<CommTableField> commTableFields = new ArrayList<>();
        if (times==0) {
            CommTableField commTableField1=new CommTableField();
            commTableField1.setComment("主键id");//注释
            commTableField1.setFieldName("id");//字段名
            commTableField1.setLength(11);//长度
            commTableField1.setTableName(tableName);//表名
            commTableField1.setIsIndex((short)1);//是否索引
            commTableField1.setDataType("int");//数据类型
            commTableField1.setIsNull((short)0);//是否为空
            commTableFields.add(0,commTableField1);
            for (Object tableDate : tableDates) {
                CommTableField commTableField=new CommTableField();
                Map<String,Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
                Integer id = (Integer)map.get("id");
                WareTemplateLibraryField wareTemplateLibraryField=new WareTemplateLibraryField();
                wareTemplateLibraryField.setTypeId(1);
                wareTemplateLibraryField.setId(id);
                this.updateById(wareTemplateLibraryField);//更改状态
                Integer length =(Integer) map.get("length");
                Integer index =(Integer) map.get("index");
                Integer required =(Integer) map.get("required");
                String dataKey =(String) map.get("dataKey");
                Integer templateLibraryFieldTypeId =(Integer) map.get("templateLibraryFieldTypeId");
                SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
                String dataType = sysTemplateLibraryFieldType.getDataKey();
                String name =(String) map.get("name");
                commTableField.setComment(name);//注释
                commTableField.setFieldName(dataKey);//字段名
                commTableField.setLength(length);//长度
                if ("datetime".equals(dataType))
                    commTableField.setLength(6);
                commTableField.setTableName(tableName);//表名
                commTableField.setIsIndex(index.shortValue());//是否索引
                commTableField.setDataType(dataType);//数据类型
                commTableField.setIsNull(required.shortValue() == 1 ? (short) 0 : (short) 1);//是否为空
                commTableFields.add(commTableField);
            }
            iCommTableFieldService.addTable(tableName,tableComment,commTableFields);
        }
        if (times>0){
            for (Object tableDate : tableDates) {
                CommTableField commTableField=new CommTableField();
                Map<String,Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
                Integer id = (Integer)map.get("id");
                WareTemplateLibraryField wareTemplateLibraryField=new WareTemplateLibraryField();
                wareTemplateLibraryField.setTypeId(1);
                wareTemplateLibraryField.setId(id);
                this.updateById(wareTemplateLibraryField);//更改状态
                Integer length =(Integer) map.get("length");
                Integer index =(Integer) map.get("index");
                Integer required =(Integer) map.get("required");
                String dataKey =(String) map.get("dataKey");
                Integer templateLibraryFieldTypeId =(Integer) map.get("templateLibraryFieldTypeId");
                SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
                String dataType = sysTemplateLibraryFieldType.getDataKey();
                String name =(String) map.get("name");
                commTableField.setComment(name);//注释
                commTableField.setFieldName(dataKey);//字段名
                commTableField.setLength(length);//长度
                if ("datetime".equals(dataType))
                    commTableField.setLength(6);
                commTableField.setTableName(tableName);//表名
                commTableField.setIsIndex(index.shortValue());//是否索引
                commTableField.setDataType(dataType);//数据类型
                commTableField.setIsNull(required.shortValue() == 1 ? (short) 0 : (short) 1);//是否为空
                iCommTableFieldService.add(tableName, commTableField);
                if (commTableField.getIsIndex() == 1) {
                    iCommTableFieldService.addIndex(tableName, dataKey);
                }
            }
        }
    }

    @Override
    public void updateOrderByIds(Integer id1, Integer id2) {
        WareTemplateLibraryField libraryField1 = this.getById(id1);
        WareTemplateLibraryField libraryField2 = this.getById(id2);
        if (libraryField1!=null&&libraryField2!=null){
            Integer order1 = libraryField1.getOrder();
            Integer order2 = libraryField2.getOrder();
            libraryField1.setOrder(order2);
            libraryField2.setOrder(order1);
            this.updateById(libraryField1);
            this.updateById(libraryField2);
        }
    }
}
