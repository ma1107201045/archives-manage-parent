package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldQueryDto;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统模板库字段表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@Service
public class SysTemplateLibraryFieldServiceImpl extends ServiceImpl<SysTemplateLibraryFieldMapper, SysTemplateLibraryField> implements ISysTemplateLibraryFieldService {

    @Autowired
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;

    @Override
    public void save(SysTemplateLibraryFieldFormDto sysTemplateLibraryFieldFormDto) {
        String dataKey = sysTemplateLibraryFieldFormDto.getDataKey();
        //参数校对
        List<Integer> ids = this.listByDataKey(sysTemplateLibraryFieldFormDto.getTemplateLibraryId(), dataKey);
        if (!ids.isEmpty())
            throw new BaseRuntimeException("当前模板库中key值不能重复");
        SysTemplateLibraryField sysTemplateLibraryField = new SysTemplateLibraryField();
        BeanUtil.copyProperties(sysTemplateLibraryFieldFormDto, sysTemplateLibraryField);
        this.save(sysTemplateLibraryField);
    }

    @Override
    public void updateById(SysTemplateLibraryFieldFormDto sysTemplateLibraryFieldFormDto) {
        Integer id = sysTemplateLibraryFieldFormDto.getId();
        String dataKey = sysTemplateLibraryFieldFormDto.getDataKey();
        //参数校对
        List<Integer> ids = this.listByDataKey(sysTemplateLibraryFieldFormDto.getTemplateLibraryId(), dataKey);
        if (!ids.isEmpty() && !ids.get(0).equals(id))
            throw new BaseRuntimeException("当前模板库中key值不能重复");
        SysTemplateLibraryField sysTemplateLibraryField = this.getById(id);
        if (sysTemplateLibraryField != null) {
            BeanUtil.copyProperties(sysTemplateLibraryFieldFormDto, sysTemplateLibraryField);
            this.updateById(sysTemplateLibraryField);
        }
    }

    @Override
    public void updateOrderByIds(Integer id1, Integer id2) {
        SysTemplateLibraryField sysTemplateLibraryField1 = this.getById(id1);
        SysTemplateLibraryField sysTemplateLibraryField2 = this.getById(id2);
        if (sysTemplateLibraryField1 != null && sysTemplateLibraryField2 != null) {
            Integer order = sysTemplateLibraryField1.getOrder();
            sysTemplateLibraryField1.setOrder(sysTemplateLibraryField2.getOrder());
            sysTemplateLibraryField2.setOrder(order);
            this.save(sysTemplateLibraryField1);
            this.save(sysTemplateLibraryField2);
        }
    }

    @Override
    public List<Integer> listByDataKey(Integer templateLibraryId, String dataKey) {
        if (templateLibraryId == null || StrUtil.isEmpty(dataKey))
            throw new BaseRuntimeException("模板库id或者模板库编号不能为空");
        QueryWrapper<SysTemplateLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysTemplateLibraryField.class, tableFieldInfo -> tableFieldInfo.getColumn().equals("id"))
                .eq(SysTemplateLibraryField::getTemplateLibraryId, templateLibraryId)
                .eq(SysTemplateLibraryField::getDataKey, dataKey);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public Page<SysTemplateLibraryField> page(SysTemplateLibraryFieldQueryDto sysTemplateLibraryFieldQueryDto) {
        Integer num = sysTemplateLibraryFieldQueryDto.getNum();
        Integer size = sysTemplateLibraryFieldQueryDto.getSize();
        Integer templateLibraryId = sysTemplateLibraryFieldQueryDto.getTemplateLibraryId();
        QueryWrapper<SysTemplateLibraryField> queryWrapper = new QueryWrapper<>();
        if (sysTemplateLibraryFieldQueryDto.getTemplateLibraryId() != null)
            queryWrapper.lambda().eq(SysTemplateLibraryField::getTemplateLibraryId, templateLibraryId);
        queryWrapper.lambda().orderByAsc(SysTemplateLibraryField::getOrder);
        Page<SysTemplateLibraryField> sysTemplateLibraryFieldPage = this.page(new Page<>(num, size), queryWrapper);
        sysTemplateLibraryFieldPage.getRecords().forEach(sysUser -> {
            sysUser.setSysTemplateLibraryFieldType(iSysTemplateLibraryFieldTypeService.getById(sysUser.getTemplateLibraryFieldTypeId()));
        });
        return sysTemplateLibraryFieldPage;
    }
}
