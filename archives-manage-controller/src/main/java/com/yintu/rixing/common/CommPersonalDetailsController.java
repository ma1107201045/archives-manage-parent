package com.yintu.rixing.common;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.common.CommPersonalDetailsFormDto;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserPasswordDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysUserService;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: mlf
 * @Date: 2021/3/9 10:21:08
 * @Version: 1.0
 */
@RestController
@RequestMapping("/common/comm-personal-details")
@Api(tags = "个人中心接口")
@ApiSupport(order = 3)
public class CommPersonalDetailsController extends Authenticator {

    @Autowired
    private ICommPersonalDetailsService iCommPersonalDetailsService;
    @Autowired
    private ISysUserService iSysUserService;

    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = " 修改个人信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改个人信息", notes = "修改个人信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated CommPersonalDetailsFormDto commPersonalDetailsFormDto) {
        iCommPersonalDetailsService.updateById(commPersonalDetailsFormDto);
        return ResultDataUtil.ok("修改个人信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = " 重置个人密码")
    @PatchMapping("/{id}")
    @ApiOperation(value = "重置个人密码", notes = "重置个人密码")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<Object> editPassword(@PathVariable Integer id, @Validated SysUserPasswordDto sysUserPasswordDto) {
        iSysUserService.resetPassword(sysUserPasswordDto);
        return ResultDataUtil.ok("重置个人密码成功");
    }

}
