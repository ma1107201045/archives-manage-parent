package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysPermissionFormDto;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.pojo.SysPermissionPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统权限表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public void save(SysPermissionFormDto sysPermissionFormDto) {
        SysPermission sysPermission = new SysPermission();
        BeanUtil.copyProperties(sysPermissionFormDto, sysPermission);
        this.save(sysPermission);
    }

    @Override
    public void removeTree(Integer id) {
        this.removeById(id);
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysPermission::getParentId, id);
        List<SysPermission> sysPermissions = this.list(queryWrapper);
        for (SysPermission sysPermission : sysPermissions) {
            this.removeTree(sysPermission.getId());
        }
    }

    @Override
    public void updateById(SysPermissionFormDto sysPermissionFormDto) {
        SysPermission sysPermission = this.getById(sysPermissionFormDto.getId());
        if (sysPermission != null) {
            BeanUtil.copyProperties(sysPermissionFormDto, sysPermission);
            this.updateById(sysPermission);
        }
    }


    @Override
    public List<TreeUtil> listTree(Integer id) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysPermission::getParentId, id).orderByAsc(SysPermission::getPriority);
        List<SysPermission> sysPermissions = this.list(queryWrapper);
        List<TreeUtil> treeUtils = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissions) {
            TreeUtil treeUtil = new TreeUtil();
            treeUtil.setId(sysPermission.getId().longValue());
            treeUtil.setLabel(sysPermission.getName());
            treeUtil.setIcon(sysPermission.getIconCls());
            treeUtil.setA_attr(BeanUtil.beanToMap(sysPermission));
            treeUtil.setChildren(this.listTree(sysPermission.getId()));
            treeUtils.add(treeUtil);
        }
        return treeUtils;
    }

    @Override
    public List<SysPermissionPojo> list(Short menu) {
        return sysPermissionMapper.selectAllPermission(menu);
    }
}
