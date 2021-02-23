package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysRoleFormDto;
import com.yintu.rixing.dto.system.SysRoleQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@ApiSupport(order = 3)
public class SysRoleController extends Authenticator implements BaseController<SysRoleFormDto, SysRoleQueryDto, SysRole, Integer> {
    @Autowired
    private ISysRoleService iSysRoleService;
    @Autowired
    private ISysPermissionService iSysPermissionService;

    @Override
    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "添加角色信息")
    @PostMapping
    @ApiOperation(value = "添加角色信息", notes = "添加角色信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> add(@Validated SysRoleFormDto formDto) {
        iSysRoleService.save(formDto);
        return ResultDataUtil.ok("添加角色信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "删除角色信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysRoleService.removeByIds(ids);
        return ResultDataUtil.ok("删除用户信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = " 修改角色信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改角色信息", notes = "修改角色信息")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysRoleFormDto dto) {
        iSysRoleService.updateById(dto);
        return ResultDataUtil.ok("修改角色信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询角色单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询角色单条信息", notes = " 查询角色单条信息")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysRole> findById(@PathVariable Integer id) {
        SysRole sysRole = iSysRoleService.getById(id);
        return ResultDataUtil.ok("查询角色单条信息成功", sysRole);
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询角色列表信息")
    @GetMapping
    @ApiOperation(value = "查询角色列表信息", notes = "查询角色列表信息", position = 5, response = SysRole.class)
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<Page<SysRole>> findPage(@Validated SysRoleQueryDto queryDto) {
        return ResultDataUtil.ok("查询角色列表信息成功", iSysRoleService.page(queryDto));
    }


    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询角色拥有权限树信息")
    @GetMapping("/sys-permission")
    @ApiOperation(value = "查询角色拥有权限树信息", notes = "查询角色拥有权限树信息")
    @ApiOperationSupport(order = 6)
    public ResultDataUtil<List<TreeUtil>> findPermissionTree() {
        List<TreeUtil> treeNodeUtils = iSysPermissionService.listTree(-1);
        return ResultDataUtil.ok("查询角色拥有权限树信息成功", treeNodeUtils);
    }


    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询角色拥有权限列表信息")
    @GetMapping("/{id}/sys-permission")
    @ApiOperation(value = "查询角色拥有权限列表信息", notes = "查询角色拥有权限列表信息")
    @ApiOperationSupport(order = 7)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<List<TreeUtil>> findPermissionTreeById(@PathVariable Integer id) {
        List<TreeUtil> treeNodeUtils = new ArrayList<>();
        iSysRoleService.sysPermissionTreeByIdAndPermissionId(id, -1, treeNodeUtils);
        return ResultDataUtil.ok("查询角色拥有权限列表信息成功", treeNodeUtils);
    }


    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询角色档案库树信息")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询角色档案库树信息", notes = "查询角色档案库树信息")
    @ApiOperationSupport(order = 8)
    public ResultDataUtil<List<TreeUtil>> findArchivesLibraryTree() {
        List<TreeUtil> treeNodeUtils = iSysPermissionService.listTree(-1);
        return ResultDataUtil.ok("查询角色档案库树信息成功", treeNodeUtils);
    }


    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询角色拥有档案库树信息")
    @GetMapping("/{id}/sys-archives-library")
    @ApiOperation(value = "查询角色拥有档案库树信息", notes = "查询角色拥有档案库树信息")
    @ApiOperationSupport(order = 9)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<List<TreeUtil>> findArchivesLibraryTreeById(@PathVariable Integer id) {
        List<TreeUtil> treeNodeUtils = new ArrayList<>();
        iSysRoleService.sysPermissionTreeByIdAndPermissionId(id, -1, treeNodeUtils);
        return ResultDataUtil.ok("查询角色拥有档案库树信息成功", treeNodeUtils);
    }
}
