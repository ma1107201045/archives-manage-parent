package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysTemplateLibraryFormDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.ISysTemplateLibraryService;
import com.yintu.rixing.system.SysTemplateLibrary;
import com.yintu.rixing.system.SysTemplateLibraryMapper;
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
        SysTemplateLibrary sysTemplateLibrary = new SysTemplateLibrary();
        List<SysTemplateLibrary> sysTemplateLibraries = this.list(new QueryWrapper<SysTemplateLibrary>().lambda().eq(SysTemplateLibrary::getNumber, sysTemplateLibraryFormDto.getNumber()));
        if (!sysTemplateLibraries.isEmpty())
            throw new BaseRuntimeException("模板编号不能重复");
        if (sysTemplateLibraryFormDto.getType() == 2) {
            sysTemplateLibrary = this.getById(sysTemplateLibraryFormDto.getParentId());
            if (sysTemplateLibrary != null) {
                if (sysTemplateLibrary.getType() == 1)
                    throw new BaseRuntimeException("模板库下边不能添加目录");
            } else sysTemplateLibrary = new SysTemplateLibrary();
        }
        BeanUtil.copyProperties(sysTemplateLibraryFormDto, sysTemplateLibrary);
        this.save(sysTemplateLibrary);
    }

    @Override
    public void removeTree(Integer id) {
        this.removeById(id);
        QueryWrapper<SysTemplateLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysTemplateLibrary::getParentId, id);
        List<SysTemplateLibrary> sysDepartments = this.list(queryWrapper);
        for (SysTemplateLibrary sysTemplateLibrary : sysDepartments) {
            this.removeTree(sysTemplateLibrary.getId());
        }
    }

    @Override
    public void updateById(SysTemplateLibraryFormDto sysTemplateLibraryFormDto) {
        List<SysTemplateLibrary> sysTemplateLibraries = this.list(new QueryWrapper<SysTemplateLibrary>().lambda().eq(SysTemplateLibrary::getNumber, sysTemplateLibraryFormDto.getNumber()));
        if (!sysTemplateLibraries.isEmpty() && !sysTemplateLibraries.get(0).getId().equals(sysTemplateLibraryFormDto.getId()))
            throw new BaseRuntimeException("模板编号不能重复");
        SysTemplateLibrary sysTemplateLibrary = new SysTemplateLibrary();
        if (sysTemplateLibraryFormDto.getType() == 2) {
            sysTemplateLibrary = this.getById(sysTemplateLibraryFormDto.getParentId());
            if (sysTemplateLibrary != null) {
                if (sysTemplateLibrary.getType() == 1)
                    throw new BaseRuntimeException("模板库下级不能添加目录");
            } else sysTemplateLibrary = new SysTemplateLibrary();
        }
        BeanUtil.copyProperties(sysTemplateLibraryFormDto, sysTemplateLibrary);
        this.updateById(sysTemplateLibrary);
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
