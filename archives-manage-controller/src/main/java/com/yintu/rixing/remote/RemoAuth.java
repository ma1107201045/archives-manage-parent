package com.yintu.rixing.remote;

import com.yintu.rixing.config.jwt.JwtTokenUtil;
import com.yintu.rixing.system.ISysRemoteUserService;
import com.yintu.rixing.system.SysRemoteUser;
import com.yintu.rixing.util.ResultDataUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/28 16:01:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("/remote/remo-auth")
public class RemoAuth {


    @Autowired
    private ISysRemoteUserService iSysRemoteUserService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 登录获取token
     *
     * @param certificateNumber 证件号
     * @param password          密码
     * @return 用户信息
     */
    @PostMapping("/login")
    public ResultDataUtil<Object> login(String certificateNumber, String password) {
        SysRemoteUser sysRemoteUser = iSysRemoteUserService.login(certificateNumber, password);
        if (sysRemoteUser != null) { //说明登录成功
            String token = jwtTokenUtil.createToken(sysRemoteUser.getCertificateNumber());
            Claims claims = jwtTokenUtil.parseJWT(token);//jwt主体

            Map<String, Object> data = new HashMap<>();
            data.put("user", sysRemoteUser);
            data.put("token", token);
            data.put("expire", claims.getExpiration());
            return ResultDataUtil.ok("登录成功", data);//下发token和用户信息
        }
        return ResultDataUtil.error("登录失败");
    }

    /**
     * 刷新token  获取新的token
     *
     * @return 返回信息
     */
    @RequestMapping("/refreshToken")
    public ResultDataUtil<Object> refreshToken() {
        String certificateNumber = jwtTokenUtil.getTokenClaimsSubject();
        //生成新的token
        String newToken = jwtTokenUtil.createToken(certificateNumber);
        return ResultDataUtil.ok("获取新的token成功", newToken);

    }

    /**
     * 获取用户信息，从token中获取到certificateNumber
     *
     * @return 返回信息
     */
    @RequestMapping("/getUserInfo")
    public ResultDataUtil<Object> getUserInfo() {
        String certificateNumber = jwtTokenUtil.getTokenClaimsSubject();
        SysRemoteUser sysRemoteUser = iSysRemoteUserService.getByCertificateNumber(certificateNumber);
        return ResultDataUtil.ok("获取用户信息成功", sysRemoteUser);
    }

}
