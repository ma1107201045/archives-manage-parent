package com.yintu.rixing.config.jwt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.SysRemoteUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.JwtSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
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

    public String getSecretKey() {
        String secret = jwtProperties.getSecret();//密钥
        return Base64.getEncoder().encodeToString(secret.getBytes());

    }


    /**
     * 生成token
     *
     * @param subject 一般为用户名
     * @return token字符串
     */
    public String createToken(String subject, Map<String, Object> map) {
        long expire = jwtProperties.getExpire();//过期失效
        String issuer = jwtProperties.getIssuer();//发行人
        Date nowDate = DateUtil.date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);//过期时间
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer(issuer)//发行人
                .setIssuedAt(nowDate) //发行时间
                .setExpiration(expireDate)//过期时间
                .setSubject(subject)//主题
                .setClaims(map)//主体部分
                .signWith(SignatureAlgorithm.HS256, getSecretKey())// 签名部分
                .compact();
    }

    /**
     * 获取token中注册信息
     *
     * @param token token
     * @return 返回信息
     */
    public Jws<Claims> parseJWT(String token) {
        try {
            if (StrUtil.isEmpty(token)) {
                throw new BaseRuntimeException("token不能为空");
            }
            token = token.substring(7);
            String secret = getSecretKey();//密钥
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
    public JwsHeader<?> parseJWTHeader(String token) {
        return parseJWT(token).getHeader();
    }

    /**
     * 获取token中注册信息 (body信息)
     *
     * @param token token
     * @return 返回信息
     */
    public Claims parseJWTBody(String token) {
        return parseJWT(token).getBody();
    }


    /**
     * 获取token中注册信息 (Signature信息)
     *
     * @param token token
     * @return 返回信息
     */
    public String parseJWTSignature(String token) {
        return parseJWT(token).getSignature();
    }


}
