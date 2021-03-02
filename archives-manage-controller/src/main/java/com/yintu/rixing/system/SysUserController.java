package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserPasswordDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020TreeUtil.ROOT_PARENT_ID1-26
 */
@RestController
@RequestMapping("/system/sys-user")
@Api(tags = "用户接口")
@ApiSupport(order = 2)
public class SysUserController extends Authenticator implements BaseController<SysUserFormDto, SysUserQueryDto, SysUser, Integer> {
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;
    @Autowired
    private ISysRoleService iSysRoleService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "添加用户信息")
    @PostMapping
    @ApiOperation(value = "添加用户信息", notes = "添加用户信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysUserFormDto formDto) {
        iSysUserService.save(formDto);
        return ResultDataUtil.ok("添加用户信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.WARN, module = "系统设置", context = "删除用户信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息", position = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysUserService.removeByIds(ids);
        return ResultDataUtil.ok("删除用户信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = " 修改用户信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysUserFormDto formDto) {
        iSysUserService.updateById(formDto);
        return ResultDataUtil.ok("修改用户信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = " 重置用户密码")
    @PatchMapping("/{id}")
    @ApiOperation(value = "重置用户密码", notes = "重置用户密码", position = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> editPassword(@PathVariable Integer id, @Validated SysUserPasswordDto sysUserPasswordDto) {
        iSysUserService.resetPassword(sysUserPasswordDto);
        return ResultDataUtil.ok("重置用户密码成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = "修改用户启用禁用状态")
    @PatchMapping("/{id}/account-enabled")
    @ApiOperation(value = "修改用户启用禁用状态", notes = "修改用户启用禁用状态", position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "accountEnabled", dataType = "int", value = "用户状态（启用 1 禁用 0）", required = true, paramType = "query"),
    })
    public ResultDataUtil<Object> editAccountEnabledOrDisabled(@PathVariable Integer id, @RequestParam Short accountEnabled) {
        iSysUserService.changeAccountEnabledOrDisabled(id, accountEnabled);
        return ResultDataUtil.ok("修改用户启用禁用状态成功");
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询用户单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询用户单条信息", notes = " 查询用户单条信息", position = 6)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysUser> findById(@PathVariable Integer id) {
        SysUser sysUser = iSysUserService.getById(id);
        return ResultDataUtil.ok("查询用户单条信息", sysUser);
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询用户列表信息")
    @GetMapping
    @ApiOperation(value = "查询用户列表信息", notes = "查询用户列表信息", position = 7)
    public ResultDataUtil<Page<SysUser>> findPage(@Validated SysUserQueryDto sysUserDto) {
        Page<SysUser> page = iSysUserService.page(sysUserDto);
        return ResultDataUtil.ok("查询用户列表信息成功", page);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询用户角色列表信息")
    @GetMapping("/sys-role")
    @ApiOperation(value = "查询用户角色列表信息", notes = "查询用户角色列表信息", position = 8)
    public ResultDataUtil<List<SysRole>> findRoles() {
        List<SysRole> sysRoles = iSysRoleService.list(new QueryWrapper<SysRole>().orderByDesc("id"));
        return ResultDataUtil.ok("查询用户角色列表信息成功", sysRoles);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询用户部门列表信息树")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询用户部门列表信息树", notes = "查询用户部门列表信息树", position = 9)
    public ResultDataUtil<List<TreeUtil>> findDepartmentTree() {
        List<TreeUtil> treeNodeUtils = iSysDepartmentService.listTree(TreeUtil.ROOT_PARENT_ID);
        return ResultDataUtil.ok("查询用户部门列表信息树成功", treeNodeUtils);
    }


    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询用户所在部门列表信息树")
    @GetMapping("/{id}/sys-department")
    @ApiOperation(value = "查询用户所在部门列表信息树", notes = "查询用户所在部门列表信息树", position = 10)
    @ApiImplicitParam(name = "id", type = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<List<TreeUtil>> findPermissionTreeById(@PathVariable Integer id) {
        List<TreeUtil> treeNodeUtils = new ArrayList<>();
        iSysUserService.sysDepartmentTreeByIdAndDepartmentId(id, TreeUtil.ROOT_PARENT_ID, treeNodeUtils);
        return ResultDataUtil.ok("查询用户所在部门列表信息树成功", treeNodeUtils);
    }


}
