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
    private ISysUserRoleService iSysUserRoleService;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ISysRolePermissionService iSysRolePermissionService;
    @Autowired
    private ISysPermissionService iSysPermissionService;
    @Autowired
    private ISysRoleArchivesLibraryService iSysRoleArchivesLibraryService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

    @Override
    public void save(SysRoleFormDto sysRoleFormDto) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(sysRoleFormDto, sysRole);
        this.save(sysRole);
        Integer id = sysRole.getId();
        this.saveSysRolePermissionsById(id, sysRoleFormDto.getPermissionIds());
        this.saveSysArchivesLibraryById(id, sysRoleFormDto.getArchivesLibraryIds());
    }

    @Override
    public void updateById(SysRoleFormDto sysRoleFormDto) {
        SysRole sysRole = this.getById(sysRoleFormDto.getId());
        if (sysRole != null) {
            BeanUtil.copyProperties(sysRoleFormDto, sysRole);
            this.updateById(sysRole);
            Integer id = sysRole.getId();
            QueryWrapper<SysRolePermission> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.lambda().eq(SysRolePermission::getRoleId, id);
            iSysRolePermissionService.remove(queryWrapper1);
            QueryWrapper<SysRoleArchivesLibrary> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(SysRoleArchivesLibrary::getRoleId, id);
            iSysRoleArchivesLibraryService.remove(queryWrapper2);
            this.saveSysRolePermissionsById(id, sysRoleFormDto.getPermissionIds());
            this.saveSysArchivesLibraryById(id, sysRoleFormDto.getArchivesLibraryIds());
        }
    }

    @Override
    public void saveSysRolePermissionsById(Integer id, Set<Integer> permissionIds) {
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
    public void saveSysArchivesLibraryById(Integer id, Set<Integer> archivesLibraryIds) {
        Collection<SysRoleArchivesLibrary> sysRoleArchivesLibraries = new LinkedHashSet<>();
        for (Integer archivesLibraryId : archivesLibraryIds) {
            SysRoleArchivesLibrary sysRoleArchivesLibrary = new SysRoleArchivesLibrary();
            sysRoleArchivesLibrary.setRoleId(id);
            sysRoleArchivesLibrary.setArchivesLibraryId(archivesLibraryId);
            sysRoleArchivesLibraries.add(sysRoleArchivesLibrary);
        }
        iSysRoleArchivesLibraryService.saveBatch(sysRoleArchivesLibraries);
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
    public List<SysUser> sysUsersById(Integer id) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysUserRole::getUserId).eq(SysUserRole::getRoleId, id);
        List<Integer> userIds = iSysUserRoleService.list(queryWrapper).stream().map(SysUserRole::getUserId).collect(Collectors.toList());
        return userIds.isEmpty() ? new ArrayList<>() : iSysUserService.listByIds(userIds);
    }


    @Override
    public List<SysPermission> sysPermissionsByIdAndPermissionId(Integer id, Integer permissionId) {
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysRolePermission::getPermissionId).eq(SysRolePermission::getRoleId, id);
        List<Integer> permissionIds = iSysRolePermissionService.list(queryWrapper).stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        if (permissionIds.isEmpty()) {
            return new ArrayList<>();
        }
        QueryWrapper<SysPermission> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().in(SysPermission::getId, permissionIds).eq(SysPermission::getParentId, permissionId);
        return iSysPermissionService.list(queryWrapper1);
    }

    @Override
    public void sysPermissionTreeByIdAndPermissionId(Integer id, Integer permissionId, List<TreeUtil> treeUtils) {
        List<SysPermission> sysPermissions = this.sysPermissionsByIdAndPermissionId(id, permissionId);
        for (SysPermission sysPermission : sysPermissions) {
            List<SysPermission> permissions = this.sysPermissionsByIdAndPermissionId(id, sysPermission.getId());
            if (!permissions.isEmpty()) {
                this.sysPermissionTreeByIdAndPermissionId(id, sysPermission.getId(), treeUtils);
            } else {
                TreeUtil treeUtil = new TreeUtil();
                treeUtil.setId(sysPermission.getId().longValue());
                treeUtil.setLabel(sysPermission.getName());
                treeUtil.setIcon(sysPermission.getIconCls());
                treeUtils.add(treeUtil);
            }
        }
    }

    @Override
    public List<SysArchivesLibrary> sysArchivesLibrariesByIdAndArchivesLibraryId(Integer id, Integer archivesLibraryId) {
        QueryWrapper<SysRoleArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysRoleArchivesLibrary::getArchivesLibraryId).eq(SysRoleArchivesLibrary::getRoleId, id);
        List<Integer> archivesLibraryIds = iSysRoleArchivesLibraryService.list(queryWrapper).stream().map(SysRoleArchivesLibrary::getArchivesLibraryId).collect(Collectors.toList());
        if (archivesLibraryIds.isEmpty()) {
            return new ArrayList<>();
        }
        QueryWrapper<SysArchivesLibrary> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().in(SysArchivesLibrary::getId, archivesLibraryIds).eq(SysArchivesLibrary::getParentId, archivesLibraryId);
        return iSysArchivesLibraryService.list(queryWrapper1);
    }

    @Override
    public void sysArchivesLibraryTreeByIdAndArchivesLibraryId(Integer id, Integer archivesLibraryId, List<TreeUtil> treeUtils) {
        List<SysArchivesLibrary> sysArchivesLibraries = this.sysArchivesLibrariesByIdAndArchivesLibraryId(id, archivesLibraryId);
        for (SysArchivesLibrary sysArchivesLibrary : sysArchivesLibraries) {
            List<SysArchivesLibrary> archivesLibraries = this.sysArchivesLibrariesByIdAndArchivesLibraryId(id, sysArchivesLibrary.getId());
            if (!archivesLibraries.isEmpty()) {
                this.sysArchivesLibraryTreeByIdAndArchivesLibraryId(id, sysArchivesLibrary.getId(), treeUtils);
            } else {
                TreeUtil treeUtil = new TreeUtil();
                treeUtil.setId(sysArchivesLibrary.getId().longValue());
                treeUtil.setLabel(sysArchivesLibrary.getName());
                treeUtils.add(treeUtil);
            }
        }
    }

}
