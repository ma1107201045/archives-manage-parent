package com.yintu.rixing.config.other;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/28 19:16:13
 * @Version: 1.0
 */
@Data
@PropertySource("classpath:jwt-property.yml")
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;//加密密钥
    private Long expire; // token有效期 秒
    private String header;// token 采用的http头，一般使用: Authorization
    private String issuer;//发行人
    private List<String> ignores; //忽略token的页面
}
