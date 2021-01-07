package com.yintu.rixing.system.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysRoleFormDto;
import com.yintu.rixing.dto.system.SysRoleQueryDto;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysRolePermissionService iSysRolePermissionService;
    @Autowired
    private ISysPermissionService iSysPermissionService;

    @Override
    public void save(SysRoleFormDto sysRoleFormDto) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(sysRoleFormDto, sysRole);
        this.save(sysRole);
        this.savePermissionsById(sysRole.getId(), sysRoleFormDto.getPermissionIds());
    }

    @Override
    public void updateById(SysRoleFormDto sysRoleFormDto) {
        SysRole sysRole = this.getById(sysRoleFormDto.getId());
        if (sysRole != null) {
            BeanUtil.copyProperties(sysRoleFormDto, sysRole);
            this.updateById(sysRole);
            QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysRolePermission::getRoleId, sysRole.getId());
            iSysRolePermissionService.remove(queryWrapper);
            this.savePermissionsById(sysRole.getId(), sysRoleFormDto.getPermissionIds());
        }
    }

    @Override
    public void savePermissionsById(Integer id, Set<Integer> permissionIds) {
        Collection<SysRolePermission> sysRolePermissions = new LinkedHashSet<>();
        for (Integer permissionId : permissionIds) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(id);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermissions.add(sysRolePermission);
        }
        iSysRolePermissionService.saveBatch(sysRolePermissions);
    }

    @Override
    public Page<SysRole> page(SysRoleQueryDto sysRoleQueryDto) {
        Integer num = sysRoleQueryDto.getNum();
        Integer size = sysRoleQueryDto.getSize();
        String name = sysRoleQueryDto.getName();
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(SysRole::getName, name == null ? "" : name);
        queryWrapper.orderByDesc("id");
        return this.page(new Page<>(num, size), queryWrapper);
    }



    @Override
    public List<SysPermission> sysPermissionsByIdAndPermissionId(Integer id, Integer permissionId) {
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysRolePermission::getPermissionId).eq(SysRolePermission::getRoleId, id);
        List<Integer> permissionIds = iSysRolePermissionService.list(queryWrapper).stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        if (permissionIds.isEmpty())
            return new ArrayList<>();
        QueryWrapper<SysPermission> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().in(SysPermission::getId, permissionIds).eq(SysPermission::getParentId, permissionId);
        return iSysPermissionService.list(queryWrapper1);
    }

    @Override
    public void sysPermissionTreeByIdAndPermissionId(Integer id, Integer parentId, List<TreeUtil> treeUtils) {
        List<SysPermission> sysPermissions = this.sysPermissionsByIdAndPermissionId(id, parentId);
        for (SysPermission sysPermission : sysPermissions) {
            List<SysPermission> permissions = this.sysPermissionsByIdAndPermissionId(id, sysPermission.getId());
            if (!permissions.isEmpty()) {
                sysPermissionTreeByIdAndPermissionId(id, sysPermission.getId(), treeUtils);
            } else {
                TreeUtil treeUtil = new TreeUtil();
                treeUtil.setId(sysPermission.getId().longValue());
                treeUtil.setLabel(sysPermission.getName());
                treeUtil.setIcon(sysPermission.getIconCls());
                treeUtils.add(treeUtil);
            }
        }
    }

}
