package com.yintu.rixing.config.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yintu.rixing.exception.BaseRuntimeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: mlf
 * @Date: 2021/1/28 19:26:30
 * @Version: 1.0
 */
@Component
public class JwtTokenUtil {
    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 生成token
     *
     * @param subject 一般为用户名
     * @return token字符串
     */
    public String createToken(String subject) {
        long expire = jwtProperties.getExpire();//过期失效
        String issuer = jwtProperties.getIssuer();//发行人
        Date nowDate = DateUtil.date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);//过期时间
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)//主题
                .setIssuer(issuer)//发行人
                .setIssuedAt(nowDate) //发行时间
                .setExpiration(expireDate)//过期时间
                //.setClaims()
                .signWith(SignatureAlgorithm.HS256, getSecretKey())// 签名部分
                .compact();
    }

    public String getSecretKey() {
        String secret = jwtProperties.getSecret();//密钥
        return Base64.getEncoder().encodeToString(secret.getBytes());

    }

    /**
     * 获取token中注册信息 (body信息)
     *
     * @param token token
     * @return 返回信息
     */
    public Claims parseJWT(String token) {
        try {
            if (StrUtil.isEmpty(token)) {
                throw new BaseRuntimeException("token不能为空");
            }
            String secret = getSecretKey();//密钥
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new BaseRuntimeException("token验证失败");
        }
    }

}
