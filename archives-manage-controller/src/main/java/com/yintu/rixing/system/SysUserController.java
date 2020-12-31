package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserPasswordDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeNodeUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/system/sys-user")
@Api(tags = "用户接口")
@ApiSort(1)
public class SysUserController extends AuthenticationController implements BaseController<SysUserFormDto, SysUser, Integer> {
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;
    @Autowired
    private ISysRoleService iSysRoleService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加用户信息")
    @PostMapping
    @ApiOperation(value = "添加用户信息", notes = "添加用户信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysUserFormDto sysUserFormDto) {
        iSysUserService.save(sysUserFormDto);
        return ResultDataUtil.ok("添加用户信息成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除用户信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息", position = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysUserService.removeByIds(ids);
        return ResultDataUtil.ok("删除用户信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改用户信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysUserFormDto sysUserFormDto) {
        iSysUserService.updateById(sysUserFormDto);
        return ResultDataUtil.ok("修改用户信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 重置密码")
    @PutMapping("/{id}/reset-password")
    @ApiOperation(value = "重置密码", notes = "重置密码", position = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> editPassword(@PathVariable Integer id, @Validated SysUserPasswordDto sysUserPasswordDto) {
        iSysUserService.resetPassword(sysUserPasswordDto);
        return ResultDataUtil.ok("重置密码成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询用户单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询用户单条信息", notes = " 查询用户单条信息", position = 5, response = SysUser.class)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysUser> findById(@PathVariable Integer id) {
        SysUser sysUser = iSysUserService.getById(id);
        return ResultDataUtil.ok("查询用户单条信息", sysUser);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询用户列表信息")
    @GetMapping
    @ApiOperation(value = "查询用户列表信息", notes = "查询用户列表信息", position = 6, response = SysUser.class)
    public ResultDataUtil<Page<SysUser>> findPage(@Validated SysUserQueryDto sysUserDto) {
        Page<SysUser> page = iSysUserService.page(sysUserDto);
        return ResultDataUtil.ok("查询用户列表信息成功", page);
    }


    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询用户部门列表信息树")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询用户部门列表信息树", notes = "查询用户部门列表信息树", position = 7)
    public ResultDataUtil<List<TreeNodeUtil>> findDepartmentTree() {
        List<TreeNodeUtil> treeNodeUtils = iSysDepartmentService.listTree(-1);
        return ResultDataUtil.ok("查询用户部门列表信息树成功", treeNodeUtils);
    }


    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询用户所在部门列表信息树")
    @GetMapping("/{id}/sys-department")
    @ApiOperation(value = "查询用户所在部门列表信息树", notes = "查询用户所在部门列表信息树", position = 8)
    @ApiImplicitParam(name = "id", type = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<List<TreeNodeUtil>> findPermissionTreeById(@PathVariable Integer id) {
        List<TreeNodeUtil> treeNodeUtils = new ArrayList<>();
        iSysUserService.sysDepartmentsByIdAndParentId(id, -1);
        return ResultDataUtil.ok("查询用户所在部门列表信息树成功", treeNodeUtils);
    }


    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询用户角色列表信息")
    @GetMapping("/sys-role")
    @ApiOperation(value = "查询用户角色列表信息", notes = "查询用户角色列表信息", position = 9)
    public ResultDataUtil<List<SysRole>> findRoles() {
        List<SysRole> sysRoles = iSysRoleService.list(new QueryWrapper<SysRole>().orderByDesc("id"));
        return ResultDataUtil.ok("查询用户角色列表信息成功", sysRoles);
    }

}
