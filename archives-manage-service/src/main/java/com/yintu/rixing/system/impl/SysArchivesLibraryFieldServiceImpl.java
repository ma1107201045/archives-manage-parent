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
        List<Integer> ids = this.listByArchivesLibraryIdDataKeys(sysArchivesLibraryFieldFormDto.getArchivesLibraryId(), new String[]{dataKey});
        if (!ids.isEmpty())
            throw new BaseRuntimeException("当前档案库中key值不能重复");
        SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
        BeanUtil.copyProperties(sysArchivesLibraryFieldFormDto, sysArchivesLibraryField);
        this.save(sysArchivesLibraryField);
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(sysArchivesLibraryField.getArchivesLibraryId());
        if (sysArchivesLibrary != null) {
            String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
            String fieldName = sysArchivesLibraryField.getDataKey();
            Integer templateLibraryFieldTypeId = sysArchivesLibraryField.getTemplateLibraryFieldTypeId();
            SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(templateLibraryFieldTypeId);
            String dataType = sysTemplateLibraryFieldType.getDataKey();

            CommTableField commTableField = new CommTableField();
            commTableField.setFieldName(fieldName);
            commTableField.setDataType(dataType);
            commTableField.setLength(sysArchivesLibraryField.getLength());
            if ("datetime".equals(dataType))
                commTableField.setLength(6);
            commTableField.setIsNull(sysArchivesLibraryField.getRequired() == 1 ? (short) 0 : (short) 1);
            commTableField.setIsIndex(sysArchivesLibraryField.getIndex().shortValue());
            commTableField.setComment(sysArchivesLibraryField.getName());
            iCommTableFieldService.add(tableName, commTableField);
            if (commTableField.getIsIndex() == 1) {
                iCommTableFieldService.addIndex(tableName, fieldName);
            }

        }
    }

    @Override
    public void updateById(SysArchivesLibraryFieldFormDto sysArchivesLibraryFieldFormDto) {
        Integer id = sysArchivesLibraryFieldFormDto.getId();
        String dataKey = sysArchivesLibraryFieldFormDto.getDataKey();
        //参数校对
        List<Integer> ids = this.listByArchivesLibraryIdDataKeys(sysArchivesLibraryFieldFormDto.getArchivesLibraryId(), new String[]{dataKey});
        if (!ids.isEmpty() && !ids.get(0).equals(id))
            throw new BaseRuntimeException("当前模板库中key值不能重复");
        SysArchivesLibraryField sysArchivesLibraryField = this.getById(id);
        if (sysArchivesLibraryField != null) {
            BeanUtil.copyProperties(sysArchivesLibraryFieldFormDto, sysArchivesLibraryField);
            this.updateById(sysArchivesLibraryField);
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
        }
    }

    @Override
    public List<Integer> listByArchivesLibraryIdDataKeys(Integer archivesLibraryId, String[] dataKeys) {
        if (archivesLibraryId == null || dataKeys == null || dataKeys.length == 0)
            throw new BaseRuntimeException("档案库id或者key不能为空");
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId)
                .eq(SysArchivesLibraryField::getDataKey, dataKeys[0]);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    /**
     * 不返回id的原因被用作回滚
     *
     * @param archivesLibraryId 档案库id
     * @param templateLibraryId 模板库id
     * @return 档案库字段集
     */
    @Override
    public List<SysArchivesLibraryField> listByArchivesLibraryIdAndTemplateLibraryId(Integer archivesLibraryId, Integer templateLibraryId) {
        if (archivesLibraryId == null || templateLibraryId == null)
            throw new BaseRuntimeException("档案库id或者模板库id不能为空");
        QueryWrapper<SysArchivesLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysArchivesLibraryField::getArchivesLibraryId, archivesLibraryId)
                .eq(SysArchivesLibraryField::getTemplateLibraryId, templateLibraryId);
        return this.list(queryWrapper);
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