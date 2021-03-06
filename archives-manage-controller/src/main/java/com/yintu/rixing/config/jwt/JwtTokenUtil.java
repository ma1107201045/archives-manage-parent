package com.yintu.rixing.config.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yintu.rixing.exception.BaseRuntimeException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2021/1/28 19:26:30
 * @Version: 1.0
 */
@Component
public class JwtTokenUtil {
    @Autowired
    private JwtProperties jwtProperties;

    public String getSecretKey() {
        //密钥
        String secret = jwtProperties.getSecret();
        return Base64.getEncoder().encodeToString(secret.getBytes());

    }


    /**
     * 生成token
     *
     * @param subject 一般为用户名
     * @return token字符串
     */
    public String createToken(String subject) {
        //过期失效
        long expire = jwtProperties.getExpire();
        //发行人
        String issuer = jwtProperties.getIssuer();
        Date nowDate = DateUtil.date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                //发行人
                .setIssuer(issuer)
                //发行时间
                .setIssuedAt(nowDate)
                //过期时间
                .setExpiration(expireDate)
                //主题
                .setSubject(subject)
                //.addClaims(map)//主体部分
                // 签名部分
                .signWith(SignatureAlgorithm.HS256, getSecretKey())
                .compact();
    }

    /**
     * 获取token中注册信息
     *
     * @param token token
     * @return 返回信息
     */
    public Jws<Claims> parseJwt(String token) {
        try {
            if (StrUtil.isEmpty(token)) {
                throw new BaseRuntimeException("token不能为空");
            }
            token = token.substring(7);
            //密钥
            String secret = getSecretKey();
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e) {
            throw new BaseRuntimeException("token验证失败");
        }
    }

    /**
     * 获取token中注册信息 (header信息)
     *
     * @param token token
     * @return 返回信息
     */
    public JwsHeader<?> parseJwtHeader(String token) {
        return parseJwt(token).getHeader();
    }

    /**
     * 获取token中注册信息 (body信息)
     *
     * @param token token
     * @return 返回信息
     */
    public Claims parseJwtPayLoad(String token) {
        return parseJwt(token).getBody();
    }


    /**
     * 获取token中注册信息 (Signature信息)
     *
     * @param token token
     * @return 返回信息
     */
    public String parseJwtSignature(String token) {
        return parseJwt(token).getSignature();
    }


}
