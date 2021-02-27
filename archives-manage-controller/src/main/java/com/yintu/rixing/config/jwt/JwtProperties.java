package com.yintu.rixing.config.jwt;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/28 19:16:13
 * @Version: 1.0
 */
@Data
@Component
public class JwtProperties {
    //加密密钥
    private String secret;
    // token有效期 秒
    private Long expire;
    // token 采用的http头，一般使用: Authorization
    private String header;
    //发行人
    private String issuer;
    //忽略token的页面
    private List<String> ignores;

    public JwtProperties() {
        this.secret = "J2m6hNsY2r5";
        this.expire = 31536000L;
        this.header = "Authorization";
        this.issuer = "system";
        List<String> ignores = new ArrayList<>();
        ignores.add("/remote/remo-authentication/login");
        ignores.add("/remote/remo-authentication/register");
        ignores.add("/remote/remo-full-text-search/**");
        this.ignores = ignores;
    }
}
