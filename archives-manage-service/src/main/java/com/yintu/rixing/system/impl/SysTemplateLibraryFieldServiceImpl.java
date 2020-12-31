package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldQueryDto;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.system.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void save(SysTemplateLibraryFieldFormDto sysTemplateLibraryFieldFormDto) {
        SysTemplateLibraryField sysTemplateLibraryField = new SysTemplateLibraryField();
        BeanUtil.copyProperties(sysTemplateLibraryFieldFormDto, sysTemplateLibraryField);
        this.save(sysTemplateLibraryField);
    }

    @Override
    public void updateById(SysTemplateLibraryFieldFormDto sysTemplateLibraryFieldFormDto) {

    }

    @Override
    public Page<SysTemplateLibraryField> page(SysTemplateLibraryFieldQueryDto sysTemplateLibraryFieldQueryDto) {
        return null;
    }
}
