package com.yintu.rixing.warehouse.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.ISysTemplateLibraryFieldTypeService;
import com.yintu.rixing.system.SysTemplateLibraryFieldType;
import com.yintu.rixing.vo.data.DataCommonFieldVo;
import com.yintu.rixing.vo.data.DataCommonVo;
import com.yintu.rixing.warehouse.IWareTemplateLibraryFieldService;
import com.yintu.rixing.warehouse.WareTemplateLibraryField;


import com.yintu.rixing.warehouse.WareTemplateLibraryFiledMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public void inWarehouse(Integer id) {
        wareTemplateLibraryFiledMapper.inWarehouse(id);
    }

    @Override
    public void outWarehouse(Integer id) {
        wareTemplateLibraryFiledMapper.outWarehouse(id);
    }

    @Override
    public void deleteWarehouse(Integer id) {
        wareTemplateLibraryFiledMapper.deleteWarehouse(id);
    }

    @Override
    public void updateWarehouse(JSONObject jsonObject, Integer id) {
        String tableName = "ware_physical_warehouse";
        JSONArray tableDates = jsonObject.getJSONArray("jsonObject");
        for (Object tableDate : tableDates) {
            Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
            Set<String> key = map.keySet();
            List<String> keys = new ArrayList<>(key);
            StringBuilder sb1 = new StringBuilder();
            for (int i = 0; i < keys.size(); i++) {
                Object o = map.get(keys.get(i));
                if (i == 0) {
                    sb1.append(keys.get(i) + "=" + "'" + o + "'");
                } else {
                    sb1.append("," + keys.get(i) + "=" + "'" + o + "'");
                }
            }
            System.out.println("111111" + key);
            System.out.println("33333" + sb1);
            wareTemplateLibraryFiledMapper.updateWarehouse(sb1, tableName, id);
        }
    }

    @Override
    public DataCommonVo findOutWarehouseBySomethings(Integer num, Integer size, JSONObject jsonObject) {
        List<DataCommonFieldVo> dataCommonFieldVos = new ArrayList<>();
        List<WareTemplateLibraryField> wareTemplateLibraryFieldList = this.list();
        for (WareTemplateLibraryField wareTemplateLibraryField : wareTemplateLibraryFieldList) {
            DataCommonFieldVo dataCommonTitleVo = new DataCommonFieldVo();
            Integer templateLibraryFieldTypeId = wareTemplateLibraryField.getTemplateLibraryFieldTypeId();
            SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
            Integer fieldTypeId = sysTemplateLibraryFieldType.getId();
            String fieldTypeDataKey = sysTemplateLibraryFieldType.getDataKey();
            String fieldTypeName = sysTemplateLibraryFieldType.getName();
            dataCommonTitleVo.setTypeId(fieldTypeId);
            dataCommonTitleVo.setTypeProp(fieldTypeDataKey);
            dataCommonTitleVo.setTypeLabel(fieldTypeName);
            dataCommonTitleVo.setNotNull(wareTemplateLibraryField.getRequired() == 1);
            dataCommonTitleVo.setProp(wareTemplateLibraryField.getDataKey());
            dataCommonTitleVo.setLabel(wareTemplateLibraryField.getName());
            dataCommonTitleVo.setQuery(wareTemplateLibraryField.getQuery().equals(EnumFlag.True.getValue()));
            dataCommonTitleVo.setTitle(wareTemplateLibraryField.getTitle().equals(EnumFlag.True.getValue()));
            dataCommonTitleVo.setForm(wareTemplateLibraryField.getForm().equals(EnumFlag.True.getValue()));
            dataCommonFieldVos.add(dataCommonTitleVo);
        }
        DataCommonFieldVo dataCommonTitleVo2 = new DataCommonFieldVo();
        dataCommonTitleVo2.setTypeProp("datetime");
        dataCommonTitleVo2.setTypeLabel("日期时间框");
        dataCommonTitleVo2.setNotNull(true);
        dataCommonTitleVo2.setProp("outWarehouseTime");
        dataCommonTitleVo2.setLabel("出库时间");
        dataCommonTitleVo2.setQuery(false);
        dataCommonTitleVo2.setTitle(true);
        dataCommonTitleVo2.setForm(false);
        dataCommonFieldVos.add(dataCommonTitleVo2);

        JSONArray tableDates = jsonObject.getJSONArray("jsonObject");
        if (tableDates== null) {
            DataCommonVo dataCommonVo = new DataCommonVo();
            dataCommonVo.setFields(dataCommonFieldVos);
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            dataCommonVo.setPage(wareTemplateLibraryFiledMapper.findAllEntityArchivesPage(page));
            return dataCommonVo;
        } else {
            StringBuilder sb1 = new StringBuilder();
            sb1.append("warehouseType = 2");
            for (Object tableDate : tableDates) {
                Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
                Set<String> key = map.keySet();
                List<String> keys = new ArrayList<>(key);
                for (int i = 0; i < keys.size(); i++) {
                    Object o = map.get(keys.get(i));
                    sb1.append("and  " + keys.get(i) + "=" + "'" + o + "'");
                }
            }
            DataCommonVo dataCommonVo = new DataCommonVo();
            dataCommonVo.setFields(dataCommonFieldVos);
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            Page<Map<String, Object>> pages = wareTemplateLibraryFiledMapper.findOutWarehouseBySomethings(page, sb1);
            dataCommonVo.setPage(pages);
            return dataCommonVo;
        }
    }

    @Override
    public DataCommonVo findOutWarehouse(Integer num, Integer size) {
        String tableName = "ware_physical_warehouse";
        if (wareTemplateLibraryFiledMapper.findTable(tableName) == 0) {
            throw new BaseRuntimeException("请先创建实体库表");
        } else {
            List<DataCommonFieldVo> dataCommonFieldVos = new ArrayList<>();
            List<WareTemplateLibraryField> wareTemplateLibraryFieldList = this.list();
            for (WareTemplateLibraryField wareTemplateLibraryField : wareTemplateLibraryFieldList) {
                DataCommonFieldVo dataCommonTitleVo = new DataCommonFieldVo();
                Integer templateLibraryFieldTypeId = wareTemplateLibraryField.getTemplateLibraryFieldTypeId();
                SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
                Integer fieldTypeId = sysTemplateLibraryFieldType.getId();
                String fieldTypeDataKey = sysTemplateLibraryFieldType.getDataKey();
                String fieldTypeName = sysTemplateLibraryFieldType.getName();
                dataCommonTitleVo.setTypeId(fieldTypeId);
                dataCommonTitleVo.setTypeProp(fieldTypeDataKey);
                dataCommonTitleVo.setTypeLabel(fieldTypeName);
                dataCommonTitleVo.setNotNull(wareTemplateLibraryField.getRequired() == 1);
                dataCommonTitleVo.setProp(wareTemplateLibraryField.getDataKey());
                dataCommonTitleVo.setLabel(wareTemplateLibraryField.getName());
                dataCommonTitleVo.setQuery(wareTemplateLibraryField.getQuery().equals(EnumFlag.True.getValue()));
                dataCommonTitleVo.setTitle(wareTemplateLibraryField.getTitle().equals(EnumFlag.True.getValue()));
                dataCommonTitleVo.setForm(wareTemplateLibraryField.getForm().equals(EnumFlag.True.getValue()));
                dataCommonFieldVos.add(dataCommonTitleVo);
            }
            DataCommonFieldVo dataCommonTitleVo2 = new DataCommonFieldVo();
            dataCommonTitleVo2.setTypeProp("datetime");
            dataCommonTitleVo2.setTypeLabel("日期时间框");
            dataCommonTitleVo2.setNotNull(true);
            dataCommonTitleVo2.setProp("outWarehouseTime");
            dataCommonTitleVo2.setLabel("出库时间");
            dataCommonTitleVo2.setQuery(false);
            dataCommonTitleVo2.setTitle(true);
            dataCommonTitleVo2.setForm(false);
            dataCommonFieldVos.add(dataCommonTitleVo2);

            DataCommonVo dataCommonVo = new DataCommonVo();
            dataCommonVo.setFields(dataCommonFieldVos);
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            dataCommonVo.setPage(wareTemplateLibraryFiledMapper.findOutWarehouse(page));
            return dataCommonVo;
        }
    }

    @Override
    public DataCommonVo findInWarehouseBySomethings(Integer num, Integer size, JSONObject jsonObject) {
        List<DataCommonFieldVo> dataCommonFieldVos = new ArrayList<>();
        List<WareTemplateLibraryField> wareTemplateLibraryFieldList = this.list();
        for (WareTemplateLibraryField wareTemplateLibraryField : wareTemplateLibraryFieldList) {
            DataCommonFieldVo dataCommonTitleVo = new DataCommonFieldVo();
            Integer templateLibraryFieldTypeId = wareTemplateLibraryField.getTemplateLibraryFieldTypeId();
            SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
            Integer fieldTypeId = sysTemplateLibraryFieldType.getId();
            String fieldTypeDataKey = sysTemplateLibraryFieldType.getDataKey();
            String fieldTypeName = sysTemplateLibraryFieldType.getName();
            dataCommonTitleVo.setTypeId(fieldTypeId);
            dataCommonTitleVo.setTypeProp(fieldTypeDataKey);
            dataCommonTitleVo.setTypeLabel(fieldTypeName);
            dataCommonTitleVo.setNotNull(wareTemplateLibraryField.getRequired() == 1);
            dataCommonTitleVo.setProp(wareTemplateLibraryField.getDataKey());
            dataCommonTitleVo.setLabel(wareTemplateLibraryField.getName());
            dataCommonTitleVo.setQuery(wareTemplateLibraryField.getQuery().equals(EnumFlag.True.getValue()));
            dataCommonTitleVo.setTitle(wareTemplateLibraryField.getTitle().equals(EnumFlag.True.getValue()));
            dataCommonTitleVo.setForm(wareTemplateLibraryField.getForm().equals(EnumFlag.True.getValue()));
            dataCommonFieldVos.add(dataCommonTitleVo);
        }
        DataCommonFieldVo dataCommonTitleVo1 = new DataCommonFieldVo();
        dataCommonTitleVo1.setTypeProp("datetime");
        dataCommonTitleVo1.setTypeLabel("日期时间框");
        dataCommonTitleVo1.setNotNull(true);
        dataCommonTitleVo1.setProp("inWarehouseTime");
        dataCommonTitleVo1.setLabel("入库时间");
        dataCommonTitleVo1.setQuery(false);
        dataCommonTitleVo1.setTitle(true);
        dataCommonTitleVo1.setForm(false);
        dataCommonFieldVos.add(dataCommonTitleVo1);

        JSONArray tableDates = jsonObject.getJSONArray("jsonObject");
        if (tableDates==null) {
            DataCommonVo dataCommonVo = new DataCommonVo();
            dataCommonVo.setFields(dataCommonFieldVos);
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            dataCommonVo.setPage(wareTemplateLibraryFiledMapper.findAllEntityArchivesPage(page));
            return dataCommonVo;
        } else {
            StringBuilder sb1 = new StringBuilder();
            sb1.append("warehouseType = 1");
            for (Object tableDate : tableDates) {
                Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
                Set<String> key = map.keySet();
                List<String> keys = new ArrayList<>(key);
                for (int i = 0; i < keys.size(); i++) {
                    Object o = map.get(keys.get(i));
                    sb1.append("and  " + keys.get(i) + "=" + "'" + o + "'");
                }
            }
            DataCommonVo dataCommonVo = new DataCommonVo();
            dataCommonVo.setFields(dataCommonFieldVos);
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            Page<Map<String, Object>> pages = wareTemplateLibraryFiledMapper.findInWarehouseBySomethings(page, sb1);
            dataCommonVo.setPage(pages);
            return dataCommonVo;
        }
    }

    @Override
    public DataCommonVo findInWarehouse(Integer num, Integer size) {
        String tableName = "ware_physical_warehouse";
        if (wareTemplateLibraryFiledMapper.findTable(tableName) == 0) {
            throw new BaseRuntimeException("请先创建实体库表");
        } else {
            List<DataCommonFieldVo> dataCommonFieldVos = new ArrayList<>();
            List<WareTemplateLibraryField> wareTemplateLibraryFieldList = this.list();
            for (WareTemplateLibraryField wareTemplateLibraryField : wareTemplateLibraryFieldList) {
                DataCommonFieldVo dataCommonTitleVo = new DataCommonFieldVo();
                Integer templateLibraryFieldTypeId = wareTemplateLibraryField.getTemplateLibraryFieldTypeId();
                SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
                Integer fieldTypeId = sysTemplateLibraryFieldType.getId();
                String fieldTypeDataKey = sysTemplateLibraryFieldType.getDataKey();
                String fieldTypeName = sysTemplateLibraryFieldType.getName();
                dataCommonTitleVo.setTypeId(fieldTypeId);
                dataCommonTitleVo.setTypeProp(fieldTypeDataKey);
                dataCommonTitleVo.setTypeLabel(fieldTypeName);
                dataCommonTitleVo.setNotNull(wareTemplateLibraryField.getRequired() == 1);
                dataCommonTitleVo.setProp(wareTemplateLibraryField.getDataKey());
                dataCommonTitleVo.setLabel(wareTemplateLibraryField.getName());
                dataCommonTitleVo.setQuery(wareTemplateLibraryField.getQuery().equals(EnumFlag.True.getValue()));
                dataCommonTitleVo.setTitle(wareTemplateLibraryField.getTitle().equals(EnumFlag.True.getValue()));
                dataCommonTitleVo.setForm(wareTemplateLibraryField.getForm().equals(EnumFlag.True.getValue()));
                dataCommonFieldVos.add(dataCommonTitleVo);
            }
            DataCommonFieldVo dataCommonTitleVo1 = new DataCommonFieldVo();
            dataCommonTitleVo1.setTypeProp("datetime");
            dataCommonTitleVo1.setTypeLabel("日期时间框");
            dataCommonTitleVo1.setNotNull(true);
            dataCommonTitleVo1.setProp("inWarehouseTime");
            dataCommonTitleVo1.setLabel("入库时间");
            dataCommonTitleVo1.setQuery(false);
            dataCommonTitleVo1.setTitle(true);
            dataCommonTitleVo1.setForm(false);
            dataCommonFieldVos.add(dataCommonTitleVo1);

            DataCommonVo dataCommonVo = new DataCommonVo();
            dataCommonVo.setFields(dataCommonFieldVos);
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            dataCommonVo.setPage(wareTemplateLibraryFiledMapper.findInWarehousePage(page));
            return dataCommonVo;
        }
    }

    @Override
    public DataCommonVo findAllEntityArchivesBySomethings(Integer num, Integer size, JSONObject jsonObject) {
        List<DataCommonFieldVo> dataCommonFieldVos = new ArrayList<>();
        List<WareTemplateLibraryField> wareTemplateLibraryFieldList = this.list();
        for (WareTemplateLibraryField wareTemplateLibraryField : wareTemplateLibraryFieldList) {
            DataCommonFieldVo dataCommonTitleVo = new DataCommonFieldVo();
            Integer templateLibraryFieldTypeId = wareTemplateLibraryField.getTemplateLibraryFieldTypeId();
            SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
            Integer fieldTypeId = sysTemplateLibraryFieldType.getId();
            String fieldTypeDataKey = sysTemplateLibraryFieldType.getDataKey();
            String fieldTypeName = sysTemplateLibraryFieldType.getName();
            dataCommonTitleVo.setTypeId(fieldTypeId);
            dataCommonTitleVo.setTypeProp(fieldTypeDataKey);
            dataCommonTitleVo.setTypeLabel(fieldTypeName);
            dataCommonTitleVo.setNotNull(wareTemplateLibraryField.getRequired() == 1);
            dataCommonTitleVo.setProp(wareTemplateLibraryField.getDataKey());
            dataCommonTitleVo.setLabel(wareTemplateLibraryField.getName());
            dataCommonTitleVo.setQuery(wareTemplateLibraryField.getQuery().equals(EnumFlag.True.getValue()));
            dataCommonTitleVo.setTitle(wareTemplateLibraryField.getTitle().equals(EnumFlag.True.getValue()));
            dataCommonTitleVo.setForm(wareTemplateLibraryField.getForm().equals(EnumFlag.True.getValue()));
            dataCommonFieldVos.add(dataCommonTitleVo);
        }
        DataCommonFieldVo dataCommonTitleVo1 = new DataCommonFieldVo();
        dataCommonTitleVo1.setTypeProp("datetime");
        dataCommonTitleVo1.setTypeLabel("日期时间框");
        dataCommonTitleVo1.setNotNull(true);
        dataCommonTitleVo1.setProp("inWarehouseTime");
        dataCommonTitleVo1.setLabel("入库时间");
        dataCommonTitleVo1.setQuery(false);
        dataCommonTitleVo1.setTitle(true);
        dataCommonTitleVo1.setForm(false);
        DataCommonFieldVo dataCommonTitleVo2 = new DataCommonFieldVo();
        dataCommonTitleVo2.setTypeProp("datetime");
        dataCommonTitleVo2.setTypeLabel("日期时间框");
        dataCommonTitleVo2.setNotNull(true);
        dataCommonTitleVo2.setProp("outWarehouseTime");
        dataCommonTitleVo2.setLabel("出库时间");
        dataCommonTitleVo2.setQuery(false);
        dataCommonTitleVo2.setTitle(true);
        dataCommonTitleVo2.setForm(false);
        dataCommonFieldVos.add(dataCommonTitleVo1);
        dataCommonFieldVos.add(dataCommonTitleVo2);

        JSONArray tableDates = jsonObject.getJSONArray("jsonObject");
        if (tableDates==null) {
            DataCommonVo dataCommonVo = new DataCommonVo();
            dataCommonVo.setFields(dataCommonFieldVos);
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            dataCommonVo.setPage(wareTemplateLibraryFiledMapper.findAllEntityArchivesPage(page));
            return dataCommonVo;
        } else {
            StringBuilder sb1 = new StringBuilder();
            for (Object tableDate : tableDates) {
                Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
                Set<String> key = map.keySet();
                List<String> keys = new ArrayList<>(key);
                System.out.println("kkkkkk"+keys);
                for (int i = 0; i < keys.size(); i++) {
                    Object o = map.get(keys.get(i));
                    if (i == 0) {
                        sb1.append(keys.get(i) + "=" + "'" + o + "'");
                    } else {
                        sb1.append("and  " + keys.get(i) + "=" + "'" + o + "'");
                    }
                }
            }
            DataCommonVo dataCommonVo = new DataCommonVo();
            dataCommonVo.setFields(dataCommonFieldVos);
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            System.out.println("ssssss"+sb1);
            Page<Map<String, Object>> pages = wareTemplateLibraryFiledMapper.findAllEntityArchivesBySomethings(page, sb1);//findAllEntityArchivesBySomethings
            dataCommonVo.setPage(pages);
            return dataCommonVo;
        }
    }

    @Override
    public DataCommonVo findAllEntityArchives(Integer num, Integer size) {
        String tableName = "ware_physical_warehouse";
        if (wareTemplateLibraryFiledMapper.findTable(tableName) == 0) {
            throw new BaseRuntimeException("请先创建实体库表");
        } else {
            List<DataCommonFieldVo> dataCommonFieldVos = new ArrayList<>();
            List<WareTemplateLibraryField> wareTemplateLibraryFieldList = this.list();
            for (WareTemplateLibraryField wareTemplateLibraryField : wareTemplateLibraryFieldList) {
                DataCommonFieldVo dataCommonTitleVo = new DataCommonFieldVo();
                Integer templateLibraryFieldTypeId = wareTemplateLibraryField.getTemplateLibraryFieldTypeId();
                SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
                Integer fieldTypeId = sysTemplateLibraryFieldType.getId();
                String fieldTypeDataKey = sysTemplateLibraryFieldType.getDataKey();
                String fieldTypeName = sysTemplateLibraryFieldType.getName();
                dataCommonTitleVo.setTypeId(fieldTypeId);
                dataCommonTitleVo.setTypeProp(fieldTypeDataKey);
                dataCommonTitleVo.setTypeLabel(fieldTypeName);
                dataCommonTitleVo.setNotNull(wareTemplateLibraryField.getRequired() == 1);
                dataCommonTitleVo.setProp(wareTemplateLibraryField.getDataKey());
                dataCommonTitleVo.setLabel(wareTemplateLibraryField.getName());
                dataCommonTitleVo.setQuery(wareTemplateLibraryField.getQuery().equals(EnumFlag.True.getValue()));
                dataCommonTitleVo.setTitle(wareTemplateLibraryField.getTitle().equals(EnumFlag.True.getValue()));
                dataCommonTitleVo.setForm(wareTemplateLibraryField.getForm().equals(EnumFlag.True.getValue()));
                dataCommonFieldVos.add(dataCommonTitleVo);
            }
            DataCommonFieldVo dataCommonTitleVo1 = new DataCommonFieldVo();
            dataCommonTitleVo1.setTypeProp("datetime");
            dataCommonTitleVo1.setTypeLabel("日期时间框");
            dataCommonTitleVo1.setNotNull(true);
            dataCommonTitleVo1.setProp("inWarehouseTime");
            dataCommonTitleVo1.setLabel("入库时间");
            dataCommonTitleVo1.setQuery(false);
            dataCommonTitleVo1.setTitle(true);
            dataCommonTitleVo1.setForm(false);
            DataCommonFieldVo dataCommonTitleVo2 = new DataCommonFieldVo();
            dataCommonTitleVo2.setTypeProp("datetime");
            dataCommonTitleVo2.setTypeLabel("日期时间框");
            dataCommonTitleVo2.setNotNull(true);
            dataCommonTitleVo2.setProp("outWarehouseTime");
            dataCommonTitleVo2.setLabel("出库时间");
            dataCommonTitleVo2.setQuery(false);
            dataCommonTitleVo2.setTitle(true);
            dataCommonTitleVo2.setForm(false);
            dataCommonFieldVos.add(dataCommonTitleVo1);
            dataCommonFieldVos.add(dataCommonTitleVo2);

            DataCommonVo dataCommonVo = new DataCommonVo();
            dataCommonVo.setFields(dataCommonFieldVos);
            Page page = new Page();
            page.setSize(size);
            page.setCurrent(num);
            dataCommonVo.setPage(wareTemplateLibraryFiledMapper.findAllEntityArchivesPage(page));
            return dataCommonVo;
        }
    }

    @Override
    public void addWarehouse(JSONObject jsonObject) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        String tableName = "ware_physical_warehouse";
        JSONArray tableDates = jsonObject.getJSONArray("jsonObject");
        for (Object tableDate : tableDates) {
            List<Object> values = new ArrayList<>();
            Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
            Set<String> key = map.keySet();
            List<String> keys = new ArrayList<>(key);
            for (String s : keys) {
                Object o = map.get(s);
                values.add(o);
            }
            StringBuilder sb1 = new StringBuilder();
            sb1.append("warehouseType");
            sb1.append(",inWarehouseTime");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("'1'");
            sb2.append("," + "'" + format + "'");
            for (int i = 0; i < keys.size(); i++) {
                sb1.append("," + keys.get(i));
            }
            for (int i = 0; i < values.size(); i++) {
                sb2.append("," + "'" + values.get(i) + "'");
            }
            System.out.println("111111" + key);
            System.out.println("222222" + values);
            System.out.println("3333333" + sb1);
            System.out.println("4444444" + sb2);
            wareTemplateLibraryFiledMapper.addWarehousee(sb1, sb2, tableName);
        }
    }

    @Override
    public Integer findTurnLeftState() {
        Integer state = 0;
        String tableName = "ware_physical_warehouse";
        if (wareTemplateLibraryFiledMapper.findTable(tableName) == 0) {
            return state;
        } else {
            List<Map<String, Object>> warePhysicalWarehouseDatas = wareTemplateLibraryFiledMapper.findTurnLeftState(tableName);
            System.out.println("11111" + warePhysicalWarehouseDatas);
            if (warePhysicalWarehouseDatas.size() == 0) {
                return state;
            } else {
                state = 1;
                return state;
            }
        }
    }

    @Override
    public Integer findTable(String tableName) {
        return wareTemplateLibraryFiledMapper.findTable(tableName);
    }

    @Override
    public void creatTable(JSONObject jsonObject) {
        String tableName = "ware_physical_warehouse";
        String tableComment = "库房管理实体库表";
        JSONArray tableDates = jsonObject.getJSONArray("jsonObject");
        List<CommTableField> commTableFields = new ArrayList<>();
        Integer table = wareTemplateLibraryFiledMapper.findTable(tableName);
        if (table == 0) {//判断表是否存在  0是不存在   1是存在
            //添加主键id
            CommTableField commTableField1 = new CommTableField();
            commTableField1.setComment("主键id");//注释
            commTableField1.setFieldName("id");//字段名
            commTableField1.setLength(11);//长度
            commTableField1.setTableName(tableName);//表名
            commTableField1.setIsIndex((short) 1);//是否索引
            commTableField1.setDataType("int");//数据类型
            commTableField1.setIsNull((short) 0);//是否为空
            commTableFields.add(0, commTableField1);
            //添加区分 出库和入库的字段
            CommTableField commTableField2 = new CommTableField();
            commTableField2.setComment("区分出库和入库 入库：1，出库：2");//注释
            commTableField2.setFieldName("warehouseType");//字段名
            commTableField2.setLength(11);//长度
            commTableField2.setTableName(tableName);//表名
            commTableField2.setIsIndex((short) 1);//是否索引
            commTableField2.setDataType("int");//数据类型
            commTableField2.setIsNull((short) 0);//是否为空
            commTableFields.add(1, commTableField2);
            for (Object tableDate : tableDates) {
                CommTableField commTableField = new CommTableField();
                Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
                System.out.println("mmmmmmm11mm" + map);
                Integer id = (Integer) map.get("id");
                WareTemplateLibraryField wareTemplateLibraryField = new WareTemplateLibraryField();
                wareTemplateLibraryField.setTypeId(1);
                wareTemplateLibraryField.setId(id);
                this.updateById(wareTemplateLibraryField);//更改TypeId状态
                Integer length = (Integer) map.get("length");
                Integer index = (Integer) map.get("index");
                Integer required = (Integer) map.get("required");
                String dataKey = (String) map.get("dataKey");
                Integer templateLibraryFieldTypeId = (Integer) map.get("templateLibraryFieldTypeId");
                SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
                String dataType = sysTemplateLibraryFieldType.getDataKey();
                String name = (String) map.get("name");
                if ("datetime".equals(dataType) || "date".equals(dataType)) {
                    commTableField.setComment(name);//注释
                    commTableField.setFieldName(dataKey);//字段名
                    // commTableField.setLength(length);//长度
                    commTableField.setTableName(tableName);//表名
                    commTableField.setIsIndex(index.shortValue());//是否索引
                    commTableField.setDataType(dataType);//数据类型
                    commTableField.setIsNull(required.shortValue() == 1 ? (short) 0 : (short) 1);//是否为空
                    commTableFields.add(commTableField);
                } else {
                    commTableField.setComment(name);//注释
                    commTableField.setFieldName(dataKey);//字段名
                    commTableField.setLength(length);//长度
                    commTableField.setTableName(tableName);//表名
                    commTableField.setIsIndex(index.shortValue());//是否索引
                    commTableField.setDataType(dataType);//数据类型
                    commTableField.setIsNull(required.shortValue() == 1 ? (short) 0 : (short) 1);//是否为空
                    commTableFields.add(commTableField);
                }
            }
            System.out.println("1111111q=" + commTableFields);
            iCommTableFieldService.addTable(tableName, tableComment, commTableFields);
        }
        if (table > 0) {//判断表是否存在  0是不存在   大于0是存在
            if (wareTemplateLibraryFiledMapper.findTurnLeftState(tableName).size() == 0) {//说明表里没数据
                iCommTableFieldService.removeTableByTableName(tableName);//删除此表
                //再重新创建表
                //添加主键id
                CommTableField commTableField1 = new CommTableField();
                commTableField1.setComment("主键id");//注释
                commTableField1.setFieldName("id");//字段名
                commTableField1.setLength(11);//长度
                commTableField1.setTableName(tableName);//表名
                commTableField1.setIsIndex((short) 1);//是否索引
                commTableField1.setDataType("int");//数据类型
                commTableField1.setIsNull((short) 0);//是否为空
                commTableFields.add(0, commTableField1);
                //添加区分 出库和入库的字段
                CommTableField commTableField2 = new CommTableField();
                commTableField2.setComment("区分出库和入库 入库：1，出库：2");//注释
                commTableField2.setFieldName("warehouseType");//字段名
                commTableField2.setLength(11);//长度
                commTableField2.setTableName(tableName);//表名
                commTableField2.setIsIndex((short) 1);//是否索引
                commTableField2.setDataType("int");//数据类型
                commTableField2.setIsNull((short) 0);//是否为空
                commTableFields.add(1, commTableField2);
                List<Integer> ids = new ArrayList<>();//typeId=1的数据id集合
                for (Object tableDate : tableDates) {
                    CommTableField commTableField = new CommTableField();
                    Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
                    System.out.println("mammmm==" + map);
                    Integer id = (Integer) map.get("id");
                    ids.add(id);
                    WareTemplateLibraryField wareTemplateLibraryField = new WareTemplateLibraryField();
                    wareTemplateLibraryField.setTypeId(1);
                    wareTemplateLibraryField.setId(id);
                    this.updateById(wareTemplateLibraryField);//更改TypeId状态
                    Integer length = (Integer) map.get("length");
                    Integer index = (Integer) map.get("index");
                    Integer required = (Integer) map.get("required");
                    String dataKey = (String) map.get("dataKey");
                    Integer templateLibraryFieldTypeId = (Integer) map.get("templateLibraryFieldTypeId");
                    SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
                    String dataType = sysTemplateLibraryFieldType.getDataKey();
                    String name = (String) map.get("name");
                    commTableField.setComment(name);//注释
                    commTableField.setFieldName(dataKey);//字段名
                    commTableField.setLength(length);//长度
                    if ("datetime".equals(dataType))
                        commTableField.setLength(0);
                    commTableField.setTableName(tableName);//表名
                    commTableField.setIsIndex(index.shortValue());//是否索引
                    commTableField.setDataType(dataType);//数据类型
                    commTableField.setIsNull(required.shortValue() == 1 ? (short) 0 : (short) 1);//是否为空
                    commTableFields.add(commTableField);
                }
                iCommTableFieldService.addTable(tableName, tableComment, commTableFields);
                QueryWrapper<WareTemplateLibraryField> queryWrapper = new QueryWrapper<>();
                WareTemplateLibraryField wareTemplateLibraryField = new WareTemplateLibraryField();
                wareTemplateLibraryField.setTypeId(0);
                queryWrapper.notIn("id", ids);
                this.update(wareTemplateLibraryField, queryWrapper);
            } else {
                for (Object tableDate : tableDates) {
                    CommTableField commTableField = new CommTableField();
                    Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
                    System.out.println("mmmmmmm=" + map);
                    Integer id = (Integer) map.get("id");
                    WareTemplateLibraryField wareTemplateLibraryField = new WareTemplateLibraryField();
                    wareTemplateLibraryField.setTypeId(1);
                    wareTemplateLibraryField.setId(id);
                    this.updateById(wareTemplateLibraryField);//更改状态
                    Integer length = (Integer) map.get("length");
                    Integer index = (Integer) map.get("index");
                    Integer required = (Integer) map.get("required");
                    String dataKey = (String) map.get("dataKey");
                    Integer templateLibraryFieldTypeId = (Integer) map.get("templateLibraryFieldTypeId");
                    SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
                    String dataType = sysTemplateLibraryFieldType.getDataKey();
                    String name = (String) map.get("name");
                    commTableField.setComment(name);//注释
                    commTableField.setFieldName(dataKey);//字段名
                    commTableField.setLength(length);//长度
                    if ("datetime".equals(dataType))
                        commTableField.setLength(0);
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
    }

    @Override
    public void updateOrderByIds(Integer id1, Integer id2) {
        WareTemplateLibraryField libraryField1 = this.getById(id1);
        WareTemplateLibraryField libraryField2 = this.getById(id2);
        if (libraryField1 != null && libraryField2 != null) {
            Integer order1 = libraryField1.getOrder();
            Integer order2 = libraryField2.getOrder();
            libraryField1.setOrder(order2);
            libraryField2.setOrder(order1);
            this.updateById(libraryField1);
            this.updateById(libraryField2);
        }
    }
}
