package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.dto.system.SysRoleFormDto;
import com.yintu.rixing.dto.system.SysRoleQueryDto;
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
public class SysRoleController extends AuthenticationController implements BaseController<SysRoleFormDto, SysRole, Integer> {
    @Autowired
    private ISysRoleService iSysRoleService;
    @Autowired
    private ISysPermissionService iSysPermissionService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加角色信息")
    @PostMapping
    @ApiOperation(value = "添加角色信息", notes = "添加角色信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysRoleFormDto sysRoleFormDto) {
        iSysRoleService.save(sysRoleFormDto);
        return ResultDataUtil.ok("添加角色信息成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除角色信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息", position = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysRoleService.removeByIds(ids);
        return ResultDataUtil.ok("删除用户信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改角色信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改角色信息", notes = "修改角色信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysRoleFormDto sysRoleFormDto) {
        iSysRoleService.updateById(sysRoleFormDto);
        return ResultDataUtil.ok("修改角色信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询角色单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询角色单条信息", notes = " 查询角色单条信息", position = 4, response = SysRole.class)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysRole> findById(@PathVariable Integer id) {
        SysRole sysRole = iSysRoleService.getById(id);
        return ResultDataUtil.ok("查询角色单条信息成功", sysRole);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询角色列表信息")
    @GetMapping
    @ApiOperation(value = "查询角色列表信息", notes = "查询角色列表信息", position = 5, response = SysRole.class)
    public ResultDataUtil<Page<SysRole>> findPage(@Validated SysRoleQueryDto sysRoleQueryDto) {
        return ResultDataUtil.ok("查询角色列表信息成功", iSysRoleService.page(sysRoleQueryDto));
    }


    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询角色权限列表树信息")
    @GetMapping("/sys-permission")
    @ApiOperation(value = "查询角色权限列表树信息", notes = "查询角色权限列表树信息", position = 6)
    public ResultDataUtil<List<TreeUtil>> findPermissionTree() {
        List<TreeUtil> treeNodeUtils = iSysPermissionService.listTree(-1);
        return ResultDataUtil.ok("查询角色权限列表树信息成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询角色拥有权限列表信息")
    @GetMapping("/{id}/sys-permission")
    @ApiOperation(value = "查询角色拥有权限列表信息", notes = "查询角色拥有权限列表信息", position = 7)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<List<TreeUtil>> findPermissionTreeById(@PathVariable Integer id) {
        List<TreeUtil> treeNodeUtils = new ArrayList<>();
        iSysRoleService.sysPermissionTreeByIdAndParentId(id, -1, treeNodeUtils);
        return ResultDataUtil.ok("查询角色拥有权限列表信息成功", treeNodeUtils);
    }
}
