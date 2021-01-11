package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysArchivesLibraryFormDto;
import com.yintu.rixing.dto.system.SysTemplateLibraryFormDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.system.SysArchivesLibraryMapper;
import com.yintu.rixing.system.SysTemplateLibrary;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统档案库表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-11
 */
@Service
public class SysArchivesLibraryServiceImpl extends ServiceImpl<SysArchivesLibraryMapper, SysArchivesLibrary> implements ISysArchivesLibraryService {


    @Override
    public void save(SysArchivesLibraryFormDto sysArchivesLibraryFormDto) {
        Short type = sysArchivesLibraryFormDto.getType();
        Integer number = sysArchivesLibraryFormDto.getNumber();
        if (type == 2 && number == null)
            throw new BaseRuntimeException("档案库编号不能为空");
        if (type == 2) {
            List<Integer> ids = this.listByNumber(number);
            if (!ids.isEmpty())
                throw new BaseRuntimeException("档案编号不能重复");
        }
        SysArchivesLibrary sysArchivesLibrary = this.getById(sysArchivesLibraryFormDto.getParentId());
        if (sysArchivesLibrary != null) {
            if (sysArchivesLibrary.getType() == 2 && type == 1)
                throw new BaseRuntimeException("档案库下边不能添加目录");
            BeanUtil.copyProperties(sysArchivesLibraryFormDto, sysArchivesLibrary);
            this.save(sysArchivesLibrary);
        }
    }

    @Override
    public void removeTree(Integer id) {
        this.removeById(id);
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary::getId)
                .eq(SysArchivesLibrary::getParentId, id);
        List<Integer> ids = this.listObjs(queryWrapper, obj -> (Integer) obj);
        for (Integer integer : ids) {
            this.removeTree(integer);
        }
    }

    @Override
    public void updateById(SysArchivesLibraryFormDto sysArchivesLibraryFormDto) {
        Integer id = sysArchivesLibraryFormDto.getId();
        Short type = sysArchivesLibraryFormDto.getType();
        Integer number = sysArchivesLibraryFormDto.getNumber();
        if (type == 2 && number == null)
            throw new BaseRuntimeException("档案编号不能为空");
        if (type == 2) {
            List<Integer> ids = this.listByNumber(number);
            if (!ids.isEmpty() && !ids.get(0).equals(id))
                throw new BaseRuntimeException("档案编号不能重复");
        }
        SysArchivesLibrary sysArchivesLibrary = this.getById(sysArchivesLibraryFormDto.getParentId());
        if (sysArchivesLibrary != null) {
            if (sysArchivesLibrary.getType() == 2 && type == 1)
                throw new BaseRuntimeException("档案库下级不能添加目录");
            BeanUtil.copyProperties(sysArchivesLibraryFormDto, sysArchivesLibrary);
            this.updateById(sysArchivesLibrary);
        }
    }

    @Override
    public List<Integer> listByNumber(Integer number) {
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary.class, tableFieldInfo -> tableFieldInfo.getColumn().equals("id"))
                .eq(SysArchivesLibrary::getNumber, number);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<TreeUtil> listTree(Integer parentId) {
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysArchivesLibrary::getParentId, parentId);
        List<SysArchivesLibrary> sysArchivesLibraries = this.list(queryWrapper);
        List<TreeUtil> treeUtils = new ArrayList<>();
        for (SysArchivesLibrary sysArchivesLibrary : sysArchivesLibraries) {
            TreeUtil treeUtil = new TreeUtil();
            treeUtil.setId(sysArchivesLibrary.getId().longValue());
            treeUtil.setLabel(sysArchivesLibrary.getName());
            treeUtil.setA_attr(BeanUtil.beanToMap(sysArchivesLibrary));
            treeUtil.setChildren(this.listTree(sysArchivesLibrary.getId()));
            treeUtils.add(treeUtil);
        }
        return treeUtils;
    }
}
