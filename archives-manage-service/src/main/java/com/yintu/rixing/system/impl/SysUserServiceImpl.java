package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TreeNodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysUserDepartmentService iSysUserDepartmentService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;
    @Autowired
    private ISysUserRoleService iSysUserRoleService;
    @Autowired
    private ISysRoleService iSysRoleService;

    @Override
    public void save(SysUserFormDto sysUserFormDto) {
        SysUser sysUser = new SysUser();
        sysUser.setAccountExpired(EnumFlag.False.getValue());
        sysUser.setAccountLocked(EnumFlag.True.getValue());
        sysUser.setCredentialsExpired(EnumFlag.False.getValue());
        sysUser.setAccountEnabled(EnumFlag.True.getValue());
        BeanUtil.copyProperties(sysUserFormDto, sysUser);
        this.save(sysUser);
        this.saveRolesById(sysUser.getId(), sysUserFormDto.getRoleIds());
    }

    @Override
    public void updateById(SysUserFormDto sysUserFormDto) {
        SysUser sysUser = this.getById(sysUserFormDto.getId());
        if (sysUser != null) {
            BeanUtil.copyProperties(sysUserFormDto, sysUser);
            this.updateById(sysUser);
            QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysUserRole::getUserId, sysUser.getId());
            iSysUserRoleService.remove(queryWrapper);
            this.saveRolesById(sysUser.getId(), sysUserFormDto.getRoleIds());
        }
    }

    @Override
    public void saveRolesById(Integer id, Set<Integer> roleIds) {
        Collection<SysUserRole> sysUserRoles = new LinkedHashSet<>();
        for (Integer roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(id);
            sysUserRole.setRoleId(roleId);
            sysUserRoles.add(sysUserRole);
        }
        iSysUserRoleService.saveBatch(sysUserRoles);
    }

    @Override
    public Page<SysUser> page(SysUserQueryDto sysUserQueryDto) {
        Integer num = sysUserQueryDto.getNum();
        Integer size = sysUserQueryDto.getSize();
        String username = sysUserQueryDto.getUsername();
        String nickname = sysUserQueryDto.getNickname();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(SysUser::getUsername, username == null ? "" : username)
                .like(SysUser::getNickname, nickname == null ? "" : nickname);
        queryWrapper.orderByDesc("id");
        Page<SysUser> sysUserPage = this.page(new Page<>(num, size), queryWrapper);
        sysUserPage.getRecords().forEach(sysUser -> {
            sysUser.setSysRoles(this.sysRolesById(sysUser.getId()));
        });
        return sysUserPage;
    }

    @Override
    public List<SysDepartment> sysDepartmentsByIdAndParentId(Integer id, Integer parentId) {
        QueryWrapper<SysUserDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysUserDepartment::getDepartmentId).eq(SysUserDepartment::getUserId, id);
        List<Integer> departmentIds = iSysUserDepartmentService.list(queryWrapper).stream().map(SysUserDepartment::getDepartmentId).collect(Collectors.toList());

        QueryWrapper<SysDepartment> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(SysDepartment::getId, parentId).in(SysDepartment::getId, departmentIds);
        return iSysDepartmentService.list(queryWrapper1);
    }

    @Override
    public void sysDepartmentTreeByIdAndParentId(Integer id, Integer parentId, List<TreeNodeUtil> treeNodeUtils) {
        List<SysDepartment> sysDepartments = this.sysDepartmentsByIdAndParentId(id, parentId);
        for (SysDepartment sysDepartment : sysDepartments) {
            List<SysDepartment> departments = this.sysDepartmentsByIdAndParentId(id, parentId);
            if (!departments.isEmpty()) {
                sysDepartmentTreeByIdAndParentId(id, sysDepartment.getId(), treeNodeUtils);
            } else {
                TreeNodeUtil treeNodeUtil = new TreeNodeUtil();
                treeNodeUtil.setId(sysDepartment.getId().longValue());
                treeNodeUtil.setLabel(sysDepartment.getName());
                treeNodeUtils.add(treeNodeUtil);
            }
        }
    }

    @Override
    public List<SysRole> sysRolesById(Integer id) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, id);
        List<Integer> roles = iSysUserRoleService.list(queryWrapper).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        return roles.isEmpty() ? new ArrayList<>() : iSysRoleService.listByIds(roles);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在这里可以自己调用数据库，对username进行查询，看看在数据库中是否存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUsername, username);
        List<SysUser> sysUsers = this.list(queryWrapper);
        if (sysUsers.isEmpty())
            throw new UsernameNotFoundException("用户名不存在");
        SysUser sysUser = sysUsers.get(0);
        //查询角色
        sysUser.setSysRoles(this.sysRolesById(sysUser.getId()));
        return sysUser;
    }


}
