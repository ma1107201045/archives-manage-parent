package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.TreeNodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
public class SysUserController extends AuthenticationController {
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;
    @Autowired
    private ISysRoleService iSysRoleService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加用户信息")
    @PostMapping
    @ApiOperation(value = "添加用户信息", notes = "添加用户信息")
    public Map<String, Object> add(@Validated SysUserFormDto sysUserFormDto) {
        iSysUserService.save(sysUserFormDto);
        return ResponseDataUtil.ok("添加用户信息成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除用户信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true, paramType = "path")
    public Map<String, Object> remove(@PathVariable Set<Integer> ids) {
        iSysUserService.removeByIds(ids);
        return ResponseDataUtil.ok("删除用户信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改用户信息")
    @PutMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "path")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    public Map<String, Object> edit(@PathVariable Integer id, @Validated SysUserFormDto sysUserFormDto) {
        iSysUserService.updateById(sysUserFormDto);
        return ResponseDataUtil.ok("修改用户信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询用户单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询用户单条信息", notes = " 查询用户单条信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> findById(@PathVariable Integer id) {
        SysUser sysUser = iSysUserService.getById(id);
        return ResponseDataUtil.ok("查询用户单条信息", sysUser);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询用户列表信息成功")
    @GetMapping
    @ApiOperation(value = "查询用户列表信息成功", notes = "查询用户列表信息成功")
    public Map<String, Object> findPage(@Validated SysUserQueryDto sysUserDto) {
        Page<SysUser> page = iSysUserService.page(sysUserDto);
        return ResponseDataUtil.ok("查询用户列表信息成功", page);
    }


    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询用户部门列表信息树")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询用户部门列表信息树", notes = "查询用户部门列表信息树")
    public Map<String, Object> findDepartmentTree() {
        List<TreeNodeUtil> treeNodeUtils = iSysDepartmentService.listTree(-1);
        return ResponseDataUtil.ok("查询用户部门列表信息树成功", treeNodeUtils);
    }


    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询用户所在部门列表信息树")
    @GetMapping("/{id}/sys-permission")
    @ApiOperation(value = "查询用户所在部门列表信息树", notes = "查询用户所在部门列表信息树")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> findPermissionTreeById(@PathVariable Integer id) {
        List<TreeNodeUtil> treeNodeUtils = new ArrayList<>();
        iSysUserService.sysDepartmentsByIdAndParentId(id, -1);
        return ResponseDataUtil.ok("查询用户所在部门列表信息树成功", treeNodeUtils);
    }



    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询用户角色列表信息")
    @GetMapping("/sys-role")
    @ApiOperation(value = "查询用户角色列表信息", notes = "查询用户角色列表信息")
    public Map<String, Object> findRoles() {
        List<SysRole> sysRoles = iSysRoleService.list(new QueryWrapper<SysRole>().orderByDesc("id"));
        return ResponseDataUtil.ok("查询用户角色列表信息成功", sysRoles);
    }

}
