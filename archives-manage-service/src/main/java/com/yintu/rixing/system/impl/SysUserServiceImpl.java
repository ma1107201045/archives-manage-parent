package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserPasswordDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        String password = sysUserFormDto.getPassword();
        if (password == null)
            throw new BaseRuntimeException("密码不能为空");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        sysUserFormDto.setPassword(passwordEncoder.encode(sysUserFormDto.getPassword()));
        SysUser sysUser = new SysUser();
        sysUser.setAccountExpired(EnumFlag.False.getValue());
        sysUser.setAccountLocked(EnumFlag.False.getValue());
        sysUser.setCredentialsExpired(EnumFlag.False.getValue());
        sysUser.setAccountEnabled(EnumFlag.True.getValue());
        BeanUtil.copyProperties(sysUserFormDto, sysUser);
        this.save(sysUser);
        this.saveRolesById(sysUser.getId(), sysUserFormDto.getRoleIds());
        this.saveDepartmentsById(sysUser.getId(), sysUserFormDto.getDepartmentIds());
    }

    @Override
    public void updateById(SysUserFormDto sysUserFormDto) {
        SysUser sysUser = this.getById(sysUserFormDto.getId());
        if (sysUser != null) {
            BeanUtil.copyProperties(sysUserFormDto, sysUser);
            this.updateById(sysUser);

            Integer id = sysUser.getId();
            QueryWrapper<SysUserRole> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.lambda().eq(SysUserRole::getUserId, id);
            iSysUserRoleService.remove(queryWrapper1);
            this.saveRolesById(sysUser.getId(), sysUserFormDto.getRoleIds());

            QueryWrapper<SysUserDepartment> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(SysUserDepartment::getUserId, id);
            iSysUserDepartmentService.remove(queryWrapper2);
            this.saveDepartmentsById(sysUser.getId(), sysUserFormDto.getDepartmentIds());
        }
    }

    @Override
    public void resetPassword(SysUserPasswordDto sysUserPasswordDto) {
        Integer id = sysUserPasswordDto.getId();
        String oldPassword = sysUserPasswordDto.getOldPassword();
        String newPassword = sysUserPasswordDto.getOldPassword();
        if (oldPassword != null && !oldPassword.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            SysUser sysUser = this.getById(id);
            if (sysUser == null)
                throw new BaseRuntimeException("当前用户不存在");
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!passwordEncoder.matches(oldPassword, sysUser.getPassword()))
                throw new BaseRuntimeException("旧密码错误");
            sysUser.setPassword(passwordEncoder.encode(newPassword));
            this.updateById(sysUser);
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
    public void saveDepartmentsById(Integer id, Set<Integer> departmentIds) {
        Collection<SysUserDepartment> sysUserDepartments = new LinkedHashSet<>();
        for (Integer departmentId : departmentIds) {
            SysUserDepartment sysUserDepartment = new SysUserDepartment();
            sysUserDepartment.setUserId(id);
            sysUserDepartment.setDepartmentId(departmentId);
            sysUserDepartments.add(sysUserDepartment);
        }
        iSysUserDepartmentService.saveBatch(sysUserDepartments);
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
    public List<SysRole> sysRolesById(Integer id) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, id);
        List<Integer> roles = iSysUserRoleService.list(queryWrapper).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        return roles.isEmpty() ? new ArrayList<>() : iSysRoleService.listByIds(roles);
    }

    @Override
    public List<SysDepartment> sysDepartmentsByIdAndDepartmentId(Integer id, Integer departmentId) {
        QueryWrapper<SysUserDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysUserDepartment::getDepartmentId).eq(SysUserDepartment::getUserId, id);
        List<Integer> departmentIds = iSysUserDepartmentService.list(queryWrapper).stream().map(SysUserDepartment::getDepartmentId).collect(Collectors.toList());

        QueryWrapper<SysDepartment> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().in(SysDepartment::getId, departmentIds).eq(SysDepartment::getParentId, departmentId);
        return iSysDepartmentService.list(queryWrapper1);
    }

    @Override
    public void sysDepartmentTreeByIdAndDepartmentId(Integer id, Integer departmentId, List<TreeUtil> treeUtils) {
        List<SysDepartment> sysDepartments = this.sysDepartmentsByIdAndDepartmentId(id, departmentId);
        for (SysDepartment sysDepartment : sysDepartments) {
            List<SysDepartment> departments = this.sysDepartmentsByIdAndDepartmentId(id, departmentId);
            if (!departments.isEmpty()) {
                sysDepartmentTreeByIdAndDepartmentId(id, sysDepartment.getId(), treeUtils);
            } else {
                TreeUtil treeUtil = new TreeUtil();
                treeUtil.setId(sysDepartment.getId().longValue());
                treeUtil.setLabel(sysDepartment.getName());
                treeUtils.add(treeUtil);
            }
        }
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
