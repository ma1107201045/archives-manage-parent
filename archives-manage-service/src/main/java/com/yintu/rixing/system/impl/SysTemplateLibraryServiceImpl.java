package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysTemplateLibraryFormDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统模板库表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@Service
public class SysTemplateLibraryServiceImpl extends ServiceImpl<SysTemplateLibraryMapper, SysTemplateLibrary> implements ISysTemplateLibraryService {

    @Override
    public void save(SysTemplateLibraryFormDto sysTemplateLibraryFormDto) {
        Short type = sysTemplateLibraryFormDto.getType();
        Integer number = sysTemplateLibraryFormDto.getNumber();
        if (type == 2 && number == null)
            throw new BaseRuntimeException("模板编号不能为空");
        if (type == 2) {
            List<Integer> ids = this.listByNumber(number);
            if (!ids.isEmpty())
                throw new BaseRuntimeException("模板编号不能重复");
        }
        SysTemplateLibrary sysTemplateLibrary = this.getById(sysTemplateLibraryFormDto.getParentId());
        if (sysTemplateLibrary != null) {
            if (sysTemplateLibrary.getType() == 2 && type == 1)
                throw new BaseRuntimeException("模板库下边不能添加目录");
            BeanUtil.copyProperties(sysTemplateLibraryFormDto, sysTemplateLibrary);
            this.save(sysTemplateLibrary);
        }
    }

    @Override
    public void removeTree(Integer id) {
        this.removeById(id);
        QueryWrapper<SysTemplateLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysTemplateLibrary::getId)
                .eq(SysTemplateLibrary::getParentId, id);
        List<Integer> ids = this.listObjs(queryWrapper, obj -> (Integer) obj);
        for (Integer integer : ids) {
            this.removeTree(integer);
        }
    }

    @Override
    public void updateById(SysTemplateLibraryFormDto sysTemplateLibraryFormDto) {
        Integer id = sysTemplateLibraryFormDto.getId();
        Short type = sysTemplateLibraryFormDto.getType();
        Integer number = sysTemplateLibraryFormDto.getNumber();
        if (type == 2 && number == null)
            throw new BaseRuntimeException("模板编号不能为空");
        if (type == 2) {
            List<Integer> ids = this.listByNumber(number);
            if (!ids.isEmpty() && !ids.get(0).equals(id))
                throw new BaseRuntimeException("模板编号不能重复");
        }
        SysTemplateLibrary sysTemplateLibrary = this.getById(sysTemplateLibraryFormDto.getParentId());
        if (sysTemplateLibrary != null) {
            if (sysTemplateLibrary.getType() == 2 && type == 1)
                throw new BaseRuntimeException("模板库下级不能添加目录");
            BeanUtil.copyProperties(sysTemplateLibraryFormDto, sysTemplateLibrary);
            this.updateById(sysTemplateLibrary);
        }
    }

    @Override
    public List<Integer> listByNumber(Integer number) {
        QueryWrapper<SysTemplateLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysTemplateLibrary.class, tableFieldInfo -> tableFieldInfo.getColumn().equals("id"))
                .eq(SysTemplateLibrary::getNumber, number);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<SysTemplateLibrary> listByType(Short type) {
        if (type == null)
            throw new BaseRuntimeException("模板库类型不能为空");
        QueryWrapper<SysTemplateLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysTemplateLibrary::getType, type);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    @Override
    public List<TreeUtil> listTree(Integer parentId) {
        QueryWrapper<SysTemplateLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysTemplateLibrary::getParentId, parentId);
        List<SysTemplateLibrary> sysTemplateLibraries = this.list(queryWrapper);
        List<TreeUtil> treeUtils = new ArrayList<>();
        for (SysTemplateLibrary sysTemplateLibrary : sysTemplateLibraries) {
            TreeUtil treeUtil = new TreeUtil();
            treeUtil.setId(sysTemplateLibrary.getId().longValue());
            treeUtil.setLabel(sysTemplateLibrary.getName());
            treeUtil.setA_attr(BeanUtil.beanToMap(sysTemplateLibrary));
            treeUtil.setChildren(this.listTree(sysTemplateLibrary.getId()));
            treeUtils.add(treeUtil);
        }
        return treeUtils;
    }
}
