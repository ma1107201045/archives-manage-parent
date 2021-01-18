package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.dto.system.SysArchivesLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysArchivesLibraryFieldQueryDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TableNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;

    @Autowired
    private ICommTableFieldService iCommTableFieldService;

    @Override
    public void save(SysArchivesLibraryFieldFormDto sysArchivesLibraryFieldFormDto) {
        String dataKey = sysArchivesLibraryFieldFormDto.getDataKey();
        //参数校对
        List<Integer> ids = this.listByArchivesLibraryIdDataKeys(sysArchivesLibraryFieldFormDto.getArchivesLibraryId(), dataKey);
        if (!ids.isEmpty())
            throw new BaseRuntimeException("当前档案库中key值不能重复");
        SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
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
    public void removeByIds(Set<Integer> ids) {
        List<SysArchivesLibraryField> sysArchivesLibraryFields = this.listByIds(ids);
        Map<Integer, List<SysArchivesLibraryField>> sysArchivesLibraryFieldGroupMap =
                sysArchivesLibraryFields.stream().collect(Collectors.groupingBy(SysArchivesLibraryField::getArchivesLibraryId));
        super.removeByIds(ids);
        for (Integer archivesLibraryId : sysArchivesLibraryFieldGroupMap.keySet()) {
            SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
            String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
            Set<String> fieldNames = sysArchivesLibraryFieldGroupMap.get(archivesLibraryId).stream().map(SysArchivesLibraryField::getDataKey).collect(Collectors.toSet());
            iCommTableFieldService.isHasDataByTableName(tableName);
            iCommTableFieldService.dropByFieldNames(tableName, fieldNames);
        }
    }

    @Override
    public void updateById(SysArchivesLibraryFieldFormDto sysArchivesLibraryFieldFormDto) {
        Integer id = sysArchivesLibraryFieldFormDto.getId();
        String dataKey = sysArchivesLibraryFieldFormDto.getDataKey();
        //参数校对
        List<Integer> ids = this.listByArchivesLibraryIdDataKeys(sysArchivesLibraryFieldFormDto.getArchivesLibraryId(), dataKey);
        if (!ids.isEmpty() && !ids.get(0).equals(id))
            throw new BaseRuntimeException("当前模板库中key值不能重复");
        SysArchivesLibraryField sysArchivesLibraryField = this.getById(id);
        if (sysArchivesLibraryField != null) {
            String oldDatakey = sysArchivesLibraryField.getDataKey();
            Short oldIndex = sysArchivesLibraryField.getIndex();
            BeanUtil.copyProperties(sysArchivesLibraryFieldFormDto, sysArchivesLibraryField);
            Short index = sysArchivesLibraryField.getIndex();
            this.updateById(sysArchivesLibraryField);
            SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(sysArchivesLibraryField.getArchivesLibraryId());
            if (sysArchivesLibrary != null) {
                CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(sysArchivesLibrary.getDataKey(), sysArchivesLibraryField);
                String tableName = commTableField.getTableName();
                iCommTableFieldService.isHasDataByTableName(tableName);
                iCommTableFieldService.alter(tableName, oldDatakey, commTableField);
                if (oldIndex == 0 && index == 1) { //之前没有现在有
                    iCommTableFieldService.addIndex(tableName, dataKey);
                } else if (oldIndex == 1 && index == 0) {//之前有现在没有
                    iCommTableFieldService.dropIndex(tableName, dataKey);
                } else if (oldIndex == 1 && index == 1) {//之前有现在也有
                    if (!oldDatakey.equals(dataKey)) {//再次判断字段名是否一样（索引名跟字段名保持一致）
                        iCommTableFieldService.dropIndex(tableName, oldDatakey);
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
            Integer order = sysArchivesLibraryField1.getOrder();
            sysArchivesLibraryField1.setOrder(sysArchivesLibraryField2.getOrder());
            sysArchivesLibraryField2.setOrder(order);
            this.saveOrUpdate(sysArchivesLibraryField1);
            this.saveOrUpdate(sysArchivesLibraryField2);
            Integer archivesLibraryId1 = sysArchivesLibraryField1.getArchivesLibraryId();
            Integer archivesLibraryId2 = sysArchivesLibraryField2.getArchivesLibraryId();
            if (archivesLibraryId1.equals(archivesLibraryId2)) {//判断两个字段是否在同一个档案库
                SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(sysArchivesLibraryField1.getArchivesLibraryId());
                if (sysArchivesLibrary != null) {
                    //改变表字段顺序
                    CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(sysArchivesLibrary.getDataKey(), sysArchivesLibraryField1);
                    String tableName = commTableField.getTableName();
                    iCommTableFieldService.isHasDataByTableName(tableName);
                    iCommTableFieldService.alterOrder(tableName, commTableField, sysArchivesLibraryField2.getDataKey());
                }
            }
        }
    }

    @Override
    public List<Integer> listByArchivesLibraryIdDataKeys(Integer archivesLibraryId, String dataKey) {
        if (archivesLibraryId == null || dataKey == null)
            throw new BaseRuntimeException("档案库id或者key不能为空");
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId)
                .eq(SysArchivesLibraryField::getDataKey, dataKey);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<Integer> listByArchivesLibraryIdAndTemplateLibraryId(Integer archivesLibraryId, Integer templateLibraryId) {
        if (archivesLibraryId == null || templateLibraryId == null)
            throw new BaseRuntimeException("档案库id或者模板库id不能为空");
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId)
                .eq(SysArchivesLibraryField::getTemplateLibraryId, templateLibraryId);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<SysArchivesLibraryField> listByArchivesLibraryId(Integer archivesLibraryId) {
        if (archivesLibraryId == null)
            throw new BaseRuntimeException("档案库id不能为空");
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId);
        queryWrapper.lambda().orderByAsc(SysArchivesLibraryField::getOrder);
        List<SysArchivesLibraryField> sysArchivesLibraryFields = this.list(queryWrapper);
        sysArchivesLibraryFields.forEach(sysArchivesLibraryField -> sysArchivesLibraryField.setSysTemplateLibraryFieldType(iSysTemplateLibraryFieldTypeService.getById(sysArchivesLibraryField.getTemplateLibraryFieldTypeId())));
        return sysArchivesLibraryFields;
    }


    @Override
    public Page<SysArchivesLibraryField> page(SysArchivesLibraryFieldQueryDto sysArchivesLibraryFieldQueryDto) {
        Integer num = sysArchivesLibraryFieldQueryDto.getNum();
        Integer size = sysArchivesLibraryFieldQueryDto.getSize();
        Integer archivesLibraryId = sysArchivesLibraryFieldQueryDto.getArchivesLibraryId();
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        if (sysArchivesLibraryFieldQueryDto.getArchivesLibraryId() != null)
            queryWrapper.lambda().eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId);
        queryWrapper.lambda().orderByAsc(SysArchivesLibraryField::getOrder);
        Page<SysArchivesLibraryField> sysArchivesLibraryFieldPage = this.page(new Page<>(num, size), queryWrapper);
        sysArchivesLibraryFieldPage.getRecords().forEach(sysArchivesLibraryField -> {
            sysArchivesLibraryField.setSysTemplateLibraryFieldType(iSysTemplateLibraryFieldTypeService.getById(sysArchivesLibraryField.getTemplateLibraryFieldTypeId()));
        });
        return sysArchivesLibraryFieldPage;
    }
}
