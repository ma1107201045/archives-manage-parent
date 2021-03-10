package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysCommonFieldLibraryFormDto;
import com.yintu.rixing.dto.system.SysCommonFieldLibraryQueryDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统公共字段库表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@Service
public class SysCommonFieldLibraryImpl extends ServiceImpl<SysCommonFieldLibraryMapper, SysCommonFieldLibrary> implements ISysCommonFieldLibraryService {

    @Autowired
    private ISysDataTypeService iSysDataTypeService;

    @Override
    public void save(SysCommonFieldLibraryFormDto sysTemplateLibraryFieldFormDto) {
        String dataKey = sysTemplateLibraryFieldFormDto.getDataKey();
        //参数校对
        List<Integer> ids = this.listByDataKey(dataKey);
        if (!ids.isEmpty()) {
            throw new BaseRuntimeException("当前模板库中key（英文字段名称）值不能重复");
        }
        SysCommonFieldLibrary sysCommonFieldLibrary = new SysCommonFieldLibrary();
        BeanUtil.copyProperties(sysTemplateLibraryFieldFormDto, sysCommonFieldLibrary);
        this.save(sysCommonFieldLibrary);
    }

    @Override
    public void updateById(SysCommonFieldLibraryFormDto sysCommonFieldLibraryFormDto) {
        Integer id = sysCommonFieldLibraryFormDto.getId();
        String dataKey = sysCommonFieldLibraryFormDto.getDataKey();
        //参数校对
        List<Integer> ids = this.listByDataKey(dataKey);
        if (!ids.isEmpty() && !ids.get(0).equals(id)) {
            throw new BaseRuntimeException("当前公共库中key（英文字段名称）值不能重复");
        }
        SysCommonFieldLibrary sysCommonFieldLibrary = this.getById(id);
        if (sysCommonFieldLibrary != null) {
            BeanUtil.copyProperties(sysCommonFieldLibraryFormDto, sysCommonFieldLibrary);
            this.updateById(sysCommonFieldLibrary);
        }
    }

    @Override
    public void updateOrderByIds(Integer id1, Integer id2) {
        SysCommonFieldLibrary sysCommonFieldLibrary1 = this.getById(id1);
        SysCommonFieldLibrary sysCommonFieldLibrary2 = this.getById(id2);
        if (sysCommonFieldLibrary1 != null && sysCommonFieldLibrary2 != null) {
            Integer order1 = sysCommonFieldLibrary1.getOrder();
            Integer order2 = sysCommonFieldLibrary2.getOrder();
            if (!order1.equals(order2)) {//如果顺序不相同需要修改
                sysCommonFieldLibrary1.setOrder(order2);
                sysCommonFieldLibrary2.setOrder(order1);
                this.saveOrUpdate(sysCommonFieldLibrary1);
                this.saveOrUpdate(sysCommonFieldLibrary2);
            }
        }
    }

    @Override
    public List<Integer> listByDataKey(String dataKey) {
        if (StrUtil.isEmpty(dataKey)) {
            throw new BaseRuntimeException("模板库id或者模板库编号不能为空");
        }
        QueryWrapper<SysCommonFieldLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysCommonFieldLibrary::getDataKey, dataKey);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public Page<SysCommonFieldLibrary> page(SysCommonFieldLibraryQueryDto sysTemplateLibraryFieldQueryDto) {
        Integer num = sysTemplateLibraryFieldQueryDto.getNum();
        Integer size = sysTemplateLibraryFieldQueryDto.getSize();
        QueryWrapper<SysCommonFieldLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(SysCommonFieldLibrary::getOrder);
        Page<SysCommonFieldLibrary> sysTemplateLibraryFieldPage = this.page(new Page<>(num, size), queryWrapper);
        sysTemplateLibraryFieldPage.getRecords().forEach(sysTemplateLibraryField -> {
            sysTemplateLibraryField.setSysDataType(iSysDataTypeService.getById(sysTemplateLibraryField.getDataTypeId()));
        });
        return sysTemplateLibraryFieldPage;
    }
}
