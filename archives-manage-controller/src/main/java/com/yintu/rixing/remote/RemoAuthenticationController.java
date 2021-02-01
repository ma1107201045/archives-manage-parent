package com.yintu.rixing.remote;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.jwt.JwtTokenUtil;
import com.yintu.rixing.dto.remote.RemoAuthenticationLoginDto;
import com.yintu.rixing.dto.remote.RemoAuthenticationRegisterDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysRemoteUserService;
import com.yintu.rixing.system.SysRemoteUser;
import com.yintu.rixing.util.IdentityIdUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.remote.RemoAuthenticationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/28 16:01:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("/remote/remo-authentication")
@Api(tags = "远程用户授权接口")
@ApiSort(1)
public class RemoAuthenticationController {

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
        String token = jwtTokenUtil.createToken(sysRemoteUser.getId().toString());
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

    @GetMapping("/{id}")
    @ApiIgnore
    public ResultDataUtil<SysRemoteUser> getById(@PathVariable Integer id, HttpServletRequest request) {
        Integer userId = IdentityIdUtil.get(request);
        SysRemoteUser sysRemoteUser = iSysRemoteUserService.getById(id);
        return ResultDataUtil.ok("获取远程用户成功", sysRemoteUser);
    }


}
