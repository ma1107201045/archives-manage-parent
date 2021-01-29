package com.yintu.rixing.remote;

import cn.hutool.core.bean.BeanUtil;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.jwt.JwtTokenUtil;
import com.yintu.rixing.dto.remote.RemoAuthenticationLoginDto;
import com.yintu.rixing.dto.remote.RemoAuthenticationRegisterDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.ISysRemoteUserService;
import com.yintu.rixing.system.SysRemoteUser;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.remote.RemoAuthenticationVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: mlf
 * @Date: 2021/1/28 16:01:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("/remote/remo-authentication")
public class RemoAuthentication {

    @Autowired
    private ISysRemoteUserService iSysRemoteUserService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Log(level = EnumLogLevel.DEBUG, module = "远程借阅", context = "远程用户登陆")
    @PostMapping("/login")
    @ApiOperation(value = "远程用户登陆", notes = "远程用户登陆")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<RemoAuthenticationVo> login(@Validated RemoAuthenticationLoginDto remoAuthenticationDto) {
        SysRemoteUser sysRemoteUser = iSysRemoteUserService.login(remoAuthenticationDto);
        String token = jwtTokenUtil.createToken(sysRemoteUser.getCertificateNumber());
        RemoAuthenticationVo remoAuthenticationVo = new RemoAuthenticationVo();
        remoAuthenticationVo.setRemoteUser(BeanUtil.beanToMap(sysRemoteUser));
        remoAuthenticationVo.setToken(token);
        return ResultDataUtil.ok("远程用户登录成功", remoAuthenticationVo);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "远程借阅", context = "远程用户注册")
    @PostMapping("/register")
    @ApiOperation(value = "远程用户注册", notes = "远程用户注册")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<Object> register(@Validated RemoAuthenticationRegisterDto remoAuthenticationRegisterDto) {
        iSysRemoteUserService.save(remoAuthenticationRegisterDto);
        return ResultDataUtil.ok("远程用户注册成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "远程借阅", context = "远程用户注册")
    @GetMapping("/{id}")
    @ApiOperation(value = "远程用户注册", notes = "远程用户注册")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<SysRemoteUser> getById(@PathVariable Integer id) {
        SysRemoteUser sysRemoteUser = iSysRemoteUserService.getById(id);
        return ResultDataUtil.ok("获取远程用户成功", sysRemoteUser);
    }

}
