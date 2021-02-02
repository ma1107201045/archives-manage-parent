package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysRemoteUserFormDto;
import com.yintu.rixing.dto.system.SysRemoteUserPasswordDto;
import com.yintu.rixing.dto.system.SysRemoteUserQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 系统远程用户表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/system/sys-remote-user")
@Api(tags = "远程用户接口")
@ApiSupport(order = 11)
public class SysRemoteUserController extends Authenticator {

    @Autowired
    private ISysRemoteUserService iSysRemoteUserService;

    @Log(level = EnumLogLevel.WARN, module = "系统设置", context = "删除远程用户信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除远程用户信息", notes = "删除远程用户信息")
    @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysRemoteUserService.removeByIds(ids);
        return ResultDataUtil.ok("删除远程用户信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = " 修改远程用户信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改远程用户信息", notes = "修改远程用户信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysRemoteUserFormDto sysRemoteUserFormDto) {
        iSysRemoteUserService.updateById(sysRemoteUserFormDto);
        return ResultDataUtil.ok("修改远程用户信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = " 重置远程用户密码")
    @PatchMapping("/{id}")
    @ApiOperation(value = "重置远程用户密码", notes = "重置远程用户密码")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<Object> editPassword(@PathVariable Integer id, @Validated SysRemoteUserPasswordDto sysRemoteUserPasswordDto) {
        iSysRemoteUserService.resetPassword(sysRemoteUserPasswordDto);
        return ResultDataUtil.ok("重置远程用户密码成功");
    }


    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询用户单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询用户单条信息", notes = " 查询用户单条信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 4)
    public ResultDataUtil<SysRemoteUser> findById(@PathVariable Integer id) {
        SysRemoteUser sysRemoteUser = iSysRemoteUserService.getById(id);
        return ResultDataUtil.ok("查询远程用户单条信息", sysRemoteUser);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询用户列表信息")
    @GetMapping
    @ApiOperation(value = "查询用户列表信息", notes = "查询用户列表信息")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<Page<SysRemoteUser>> findPage(@Validated SysRemoteUserQueryDto sysRemoteUserQueryDto) {
        Page<SysRemoteUser> page = iSysRemoteUserService.page(sysRemoteUserQueryDto);
        return ResultDataUtil.ok("查询用户列表信息成功", page);
    }

}
