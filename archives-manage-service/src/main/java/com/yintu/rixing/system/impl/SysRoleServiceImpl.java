package com.yintu.rixing.system.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysRoleFormDto;
import com.yintu.rixing.dto.system.SysRoleQueryDto;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.TreeNodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
    public List<SysPermission> sysPermissionsByIdAndParentId(Integer id, Integer parentId) {
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysRolePermission::getPermissionId).eq(SysRolePermission::getRoleId, id);
        List<Integer> permissionIds = iSysRolePermissionService.list(queryWrapper).stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());

        QueryWrapper<SysPermission> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(SysPermission::getId, parentId).in(SysPermission::getId, permissionIds);
        return iSysPermissionService.list(queryWrapper1);
    }

    @Override
    public void sysPermissionsTreeByIdAndParentId(Integer id, Integer parentId, List<TreeNodeUtil> treeNodeUtils) {
        List<SysPermission> sysPermissions = this.sysPermissionsByIdAndParentId(id, parentId);
        for (SysPermission sysPermission : sysPermissions) {
            List<SysPermission> permissions = this.sysPermissionsByIdAndParentId(id, parentId);
            if (!permissions.isEmpty()) {
                sysPermissionsTreeByIdAndParentId(id, sysPermission.getId(), treeNodeUtils);
            } else {
                TreeNodeUtil treeNodeUtil = new TreeNodeUtil();
                treeNodeUtil.setId(sysPermission.getId().longValue());
                treeNodeUtil.setLabel(sysPermission.getName());
                treeNodeUtil.setIcon(sysPermission.getImgPath());
                treeNodeUtils.add(treeNodeUtil);
            }
        }
    }

}
