package com.yintu.rixing.system;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysPermissionFormDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统权限表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/system/sys-permission")
@Api(tags = "权限接口")
@ApiSupport(order = 4)
public class SysPermissionController extends Authenticator {

    @Autowired
    private ISysPermissionService iSysPermissionService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "添加权限信息")
    @PostMapping
    @ApiOperation(value = "添加权限信息", notes = "添加权限信息", position = 1)
    public Map<String, Object> add(@Validated SysPermissionFormDto sysPermissionFomDto) {
        iSysPermissionService.save(sysPermissionFomDto);
        return ResponseDataUtil.ok("添加权限信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "删除权限信息")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除权限信息", notes = "删除权限信息", position = 2)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> remove(@PathVariable Integer id) {
        iSysPermissionService.removeTree(id);
        return ResponseDataUtil.ok("删除权限信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = " 修改权限信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改权限信息", notes = "修改权限信息", position = 3)
    @ApiImplicitParam(name = "id", type = "int", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> edit(@PathVariable Integer id, @Validated SysPermissionFormDto sysPermissionFomDto) {
        iSysPermissionService.updateById(sysPermissionFomDto);
        return ResponseDataUtil.ok("修改权限信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询权限单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询权限单条信息", notes = " 查询权限单条信息", position = 4)
    @ApiImplicitParam(name = "id", type = "int", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> findById(@PathVariable Integer id) {
        SysPermission sysPermission = iSysPermissionService.getById(id);
        return ResponseDataUtil.ok("查询权限单条信息", sysPermission);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询权限列表信息树")
    @GetMapping
    @ApiOperation(value = "查询权限列表信息树", notes = "查询权限列表信息树", position = 5)
    public Map<String, Object> findTree() {
        List<TreeUtil> treeNodeUtils = iSysPermissionService.listTree(TreeUtil.ROOT_PARENT_ID);
        return ResponseDataUtil.ok("查询权限列表信息树成功", treeNodeUtils);
    }

}
