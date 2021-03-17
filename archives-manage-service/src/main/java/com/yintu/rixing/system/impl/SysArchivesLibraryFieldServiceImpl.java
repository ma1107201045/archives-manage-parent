package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.dto.system.SysArchivesLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysArchivesLibraryFieldQueryDto;
import com.yintu.rixing.enumobject.EnumFieldType;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TableNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统档案库字段表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-14
 */
@Service
public class SysArchivesLibraryFieldServiceImpl extends ServiceImpl<SysArchivesLibraryFieldMapper, SysArchivesLibraryField> implements ISysArchivesLibraryFieldService {
    @Autowired
    private ISysCommonFieldLibraryService iSysCommonFieldLibraryService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private ISysDataTypeService iSysDataTypeService;
    @Autowired
    private ICommTableFieldService iCommTableFieldService;

    @Override
    public void save(SysArchivesLibraryFieldFormDto sysArchivesLibraryFieldFormDto) {
        Integer archivesLibraryId = sysArchivesLibraryFieldFormDto.getArchivesLibraryId();
        String dataKey = sysArchivesLibraryFieldFormDto.getDataKey();
        //参数校对
        List<Integer> ids = this.listByArchivesLibraryIdAndDataKeys(archivesLibraryId, Collections.singletonList(dataKey));
        if (!ids.isEmpty()) {
            throw new BaseRuntimeException("当前档案库中key值(英文字段名)不能重复");
        }
        SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
        sysArchivesLibraryField.setFromType(EnumFieldType.CUSTOM.getValue());
        BeanUtil.copyProperties(sysArchivesLibraryFieldFormDto, sysArchivesLibraryField);
        this.save(sysArchivesLibraryField);
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(sysArchivesLibraryField.getArchivesLibraryId());
        if (sysArchivesLibrary != null) {
            CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(sysArchivesLibrary.getDataKey(), sysArchivesLibraryField);
            String tableName = commTableField.getTableName();
            iCommTableFieldService.isHasDataByTableName(tableName);
            iCommTableFieldService.add(tableName, commTableField);
            if (commTableField.getIsIndex() == 1) {
                iCommTableFieldService.addIndex(tableName, dataKey);
            }
        }
    }

    @Override
    public void chooseSysCommonFieldLibrary(Integer archivesLibraryId, List<Integer> commonFieldLibraryIds) {
        List<SysCommonFieldLibrary> sysCommonFieldLibraries = iSysCommonFieldLibraryService.listByIds(commonFieldLibraryIds);
        List<String> dataKeys = sysCommonFieldLibraries.stream().map(SysCommonFieldLibrary::getDataKey).collect(Collectors.toList());
        //参数校对
        List<Integer> ids = this.listByArchivesLibraryIdAndDataKeys(archivesLibraryId, dataKeys);
        if (!ids.isEmpty()) {
            throw new BaseRuntimeException("当前档案库中key值(英文字段名)不能重复");
        }
        List<SysArchivesLibraryField> sysArchivesLibraryFields = new ArrayList<>();
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
        if (sysArchivesLibrary != null) {
            String dataKey = sysArchivesLibrary.getDataKey();
            //选择的公共库需要同步
            for (SysCommonFieldLibrary sysCommonFieldLibrary : sysCommonFieldLibraries) {
                SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
                BeanUtil.copyProperties(sysCommonFieldLibrary, sysArchivesLibraryField, "id");
                sysArchivesLibraryField.setArchivesLibraryId(archivesLibraryId);
                sysArchivesLibraryField.setFromType(EnumFieldType.COMMON.getValue());
                sysArchivesLibraryFields.add(sysArchivesLibraryField);
                CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(dataKey, sysArchivesLibraryField);
                String tableName = commTableField.getTableName();
                iCommTableFieldService.add(tableName, commTableField);
                if (commTableField.getIsIndex() == 1) {
                    iCommTableFieldService.addIndex(tableName, dataKey);
                }
            }
            this.saveBatch(sysArchivesLibraryFields);
        }
    }

    @Override
    public void removeByIds(Set<Integer> ids) {

        List<SysArchivesLibraryField> sysArchivesLibraryFields = this.listByIds(ids);
        sysArchivesLibraryFields.forEach(sysArchivesLibraryField -> {
            if (sysArchivesLibraryField.getFromType().equals(EnumFieldType.BASE.getValue())) {
                throw new BaseRuntimeException("系统默认key不能删除");
            }
        });
        super.removeByIds(ids);
        Map<Integer, List<SysArchivesLibraryField>> sysArchivesLibraryFieldGroupMap = sysArchivesLibraryFields.stream().collect(Collectors.groupingBy(SysArchivesLibraryField::getArchivesLibraryId));
        for (Integer archivesLibraryId : sysArchivesLibraryFieldGroupMap.keySet()) {
            SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
            String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
            Set<String> fieldNames = sysArchivesLibraryFieldGroupMap.get(archivesLibraryId).stream().map(SysArchivesLibraryField::getDataKey).collect(Collectors.toSet());
            iCommTableFieldService.isHasDataByTableName(tableName);
            iCommTableFieldService.dropByFieldNames(tableName, fieldNames);
        }

    }

    @Override
    public void removeByArchivesLibraryId(Integer archivesLibraryId) {
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId);
        this.remove(queryWrapper);
    }

    @Override
    public void updateById(SysArchivesLibraryFieldFormDto sysArchivesLibraryFieldFormDto) {
        Integer id = sysArchivesLibraryFieldFormDto.getId();
        String dataKey = sysArchivesLibraryFieldFormDto.getDataKey();
        //参数校对
        List<Integer> ids = this.listByArchivesLibraryIdAndDataKeys(sysArchivesLibraryFieldFormDto.getArchivesLibraryId(), Collections.singletonList(dataKey));
        if (!ids.isEmpty() && !ids.get(0).equals(id)) {
            throw new BaseRuntimeException("当前模板库中key值不能重复");
        }
        SysArchivesLibraryField sysArchivesLibraryField = this.getById(id);
        if (sysArchivesLibraryField != null) {
            if (sysArchivesLibraryField.getFromType().equals(EnumFieldType.BASE.getValue())) {
                throw new BaseRuntimeException("系统默认key不能修改");
            }
            String oldDataKey = sysArchivesLibraryField.getDataKey();
            Short oldIndex = sysArchivesLibraryField.getIndex();
            BeanUtil.copyProperties(sysArchivesLibraryFieldFormDto, sysArchivesLibraryField);
            Short index = sysArchivesLibraryField.getIndex();
            this.updateById(sysArchivesLibraryField);
            SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(sysArchivesLibraryField.getArchivesLibraryId());
            if (sysArchivesLibrary != null) {
                CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(sysArchivesLibrary.getDataKey(), sysArchivesLibraryField);
                String tableName = commTableField.getTableName();
                iCommTableFieldService.isHasDataByTableName(tableName);
                iCommTableFieldService.alter(tableName, oldDataKey, commTableField);
                //之前没有现在有
                if (oldIndex == 0 && index == 1) {
                    iCommTableFieldService.addIndex(tableName, dataKey);
                    //之前有现在没有
                } else if (oldIndex == 1 && index == 0) {
                    iCommTableFieldService.dropIndex(tableName, dataKey);
                    //之前有现在也有
                } else if (oldIndex == 1 && index == 1) {
                    //再次判断字段名是否一样（索引名跟字段名保持一致）
                    if (!oldDataKey.equals(dataKey)) {
                        iCommTableFieldService.dropIndex(tableName, oldDataKey);
                        iCommTableFieldService.addIndex(tableName, dataKey);

                    }
                }
            }
        }
    }

    @Override
    public void updateOrderByIds(Integer id1, Integer id2) {
        SysArchivesLibraryField sysArchivesLibraryField1 = this.getById(id1);
        SysArchivesLibraryField sysArchivesLibraryField2 = this.getById(id2);
        if (sysArchivesLibraryField1 != null && sysArchivesLibraryField2 != null) {
            Integer order1 = sysArchivesLibraryField1.getOrder();
            Integer order2 = sysArchivesLibraryField2.getOrder();
            //如果顺序不相同需要修改
            if (!order1.equals(order2)) {
                sysArchivesLibraryField1.setOrder(order2);
                sysArchivesLibraryField2.setOrder(order1);
                this.saveOrUpdate(sysArchivesLibraryField1);
                this.saveOrUpdate(sysArchivesLibraryField2);
            }
        }
    }

    @Override
    public List<Integer> listByArchivesLibraryIdAndDataKeys(Integer archivesLibraryId, List<String> keys) {
        if (archivesLibraryId == null || keys == null || keys.isEmpty()) {
            throw new BaseRuntimeException("档案库id或者key不能为空");
        }
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibraryField::getId)
                .eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId)
                .in(SysArchivesLibraryField::getDataKey, keys);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<Integer> listByArchivesLibraryIdAndSystem(Integer archivesLibraryId, Short system) {
        if (archivesLibraryId == null || system == null) {
            throw new BaseRuntimeException("档案库id或者系统字段不能为空");
        }
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibraryField::getId)
                .eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<Integer> listByArchivesLibraryIdAndTemplateLibraryId(Integer archivesLibraryId, Integer templateLibraryId) {
        if (archivesLibraryId == null || templateLibraryId == null) {
            throw new BaseRuntimeException("档案库id或者模板库id不能为空");
        }
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibraryField::getId)
                .eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }


    @Override
    public List<SysArchivesLibraryField> listByArchivesLibraryId(Integer archivesLibraryId) {
        if (archivesLibraryId == null) {
            throw new BaseRuntimeException("档案库id不能为空");
        }
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId);
        queryWrapper.lambda().orderByAsc(SysArchivesLibraryField::getOrder);
        List<SysArchivesLibraryField> sysArchivesLibraryFields = this.list(queryWrapper);
        sysArchivesLibraryFields.forEach(sysArchivesLibraryField -> sysArchivesLibraryField.setSysDataType(iSysDataTypeService.getById(sysArchivesLibraryField.getDataTypeId())));
        return sysArchivesLibraryFields;
    }

    @Override
    public List<SysArchivesLibraryField> listByArchivesLibraryIdAndQuery(Integer archivesLibraryId) {
        return this.listByArchivesLibraryId(archivesLibraryId)
                .stream()
                .filter(sysArchivesLibraryField -> sysArchivesLibraryField.getQuery().equals(EnumFlag.True.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysArchivesLibraryField> listByArchivesLibraryIdAndTitle(Integer archivesLibraryId) {
        return this.listByArchivesLibraryId(archivesLibraryId)
                .stream()
                .filter(sysArchivesLibraryField -> sysArchivesLibraryField.getTitle().equals(EnumFlag.True.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysArchivesLibraryField> listByArchivesLibraryIdAndForm(Integer archivesLibraryId) {
        return this.listByArchivesLibraryId(archivesLibraryId)
                .stream()
                .filter(sysArchivesLibraryField -> sysArchivesLibraryField.getForm().equals(EnumFlag.True.getValue()))
                .collect(Collectors.toList());
    }


    @Override
    public Page<SysArchivesLibraryField> page(SysArchivesLibraryFieldQueryDto sysArchivesLibraryFieldQueryDto) {
        Integer num = sysArchivesLibraryFieldQueryDto.getNum();
        Integer size = sysArchivesLibraryFieldQueryDto.getSize();
        Integer archivesLibraryId = sysArchivesLibraryFieldQueryDto.getArchivesLibraryId();
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId)
                //排除基础字段库
                .gt(SysArchivesLibraryField::getFromType, EnumFieldType.BASE.getValue())
                .orderByAsc(SysArchivesLibraryField::getOrder);
        Page<SysArchivesLibraryField> sysArchivesLibraryFieldPage = this.page(new Page<>(num, size), queryWrapper);
        sysArchivesLibraryFieldPage.getRecords().forEach(sysArchivesLibraryField -> {
            sysArchivesLibraryField.setSysDataType(iSysDataTypeService.getById(sysArchivesLibraryField.getDataTypeId()));
        });
        return sysArchivesLibraryFieldPage;
    }
}
