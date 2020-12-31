package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.dto.system.SysRoleFormDto;
import com.yintu.rixing.dto.system.SysRoleQueryDto;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
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
 * 系统角色表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/system/sys-role")
@Api(tags = "角色接口")
@ApiSort(2)
public class SysRoleController extends AuthenticationController implements BaseController<SysRoleFormDto, Integer> {
    @Autowired
    private ISysRoleService iSysRoleService;
    @Autowired
    private ISysPermissionService iSysPermissionService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加角色信息")
    @PostMapping
    @ApiOperation(value = "添加角色信息", notes = "添加角色信息", position = 1)
    public Map<String, Object> add(@Validated SysRoleFormDto sysRoleFormDto) {
        iSysRoleService.save(sysRoleFormDto);
        return ResponseDataUtil.ok("添加角色信息成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除角色信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息", position = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, value = "主键id集", required = true, paramType = "path")
    public Map<String, Object> remove(@PathVariable Set<Integer> ids) {
        iSysRoleService.removeByIds(ids);
        return ResponseDataUtil.ok("删除用户信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改角色信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改角色信息", notes = "修改角色信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> edit(@PathVariable Integer id, @Validated SysRoleFormDto sysRoleFormDto) {
        iSysRoleService.updateById(sysRoleFormDto);
        return ResponseDataUtil.ok("修改角色信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询角色单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询角色单条信息", notes = " 查询角色单条信息", position = 4, response = SysRole.class)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> findById(@PathVariable Integer id) {
        SysRole sysRole = iSysRoleService.getById(id);
        return ResponseDataUtil.ok("查询角色单条信息成功", sysRole);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询角色列表信息")
    @GetMapping
    @ApiOperation(value = "查询角色列表信息", notes = "查询角色列表信息", position = 5, response = SysRole.class)
    public Map<String, Object> findPage(@Validated SysRoleQueryDto sysRoleQueryDto) {
        return ResponseDataUtil.ok("查询角色列表信息成功", iSysRoleService.page(sysRoleQueryDto));
    }


    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询角色权限列表树信息")
    @GetMapping("/sys-permission")
    @ApiOperation(value = "查询角色权限列表树信息", notes = "查询角色权限列表树信息", position = 6)
    public Map<String, Object> findPermissionTree() {
        List<TreeNodeUtil> treeNodeUtils = iSysPermissionService.listTree(-1);
        return ResponseDataUtil.ok("查询角色权限列表树信息成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询角色拥有权限列表信息")
    @GetMapping("/{id}/sys-permission")
    @ApiOperation(value = "查询角色拥有权限列表信息", notes = "查询角色拥有权限列表信息", position = 7)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> findPermissionTreeById(@PathVariable Integer id) {
        List<TreeNodeUtil> treeNodeUtils = new ArrayList<>();
        iSysRoleService.sysPermissionTreeByIdAndParentId(id, -1, treeNodeUtils);
        return ResponseDataUtil.ok("查询角色拥有权限列表信息成功", treeNodeUtils);
    }
}
