package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldQueryDto;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        SysTemplateLibraryField sysTemplateLibraryField = new SysTemplateLibraryField();
        BeanUtil.copyProperties(sysTemplateLibraryFieldFormDto, sysTemplateLibraryField);
        this.save(sysTemplateLibraryField);
    }

    @Override
    public void updateById(SysTemplateLibraryFieldFormDto sysTemplateLibraryFieldFormDto) {
        SysTemplateLibraryField sysTemplateLibraryField = this.getById(sysTemplateLibraryFieldFormDto.getId());
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
