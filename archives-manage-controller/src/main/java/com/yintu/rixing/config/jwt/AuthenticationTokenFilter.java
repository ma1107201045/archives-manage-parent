package com.yintu.rixing.config.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yintu.rixing.config.jwt.JwtProperties;
import com.yintu.rixing.config.jwt.JwtTokenUtil;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.util.PathIgnoringUtil;
import com.yintu.rixing.util.ResultDataUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/28 15:21:35
 * @Version: 1.0
 */
@WebFilter(urlPatterns = "/remote/*", filterName = "remoteLoginFilter")
@Component
public class AuthenticationTokenFilter implements Filter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = StrUtil.split(request.getRequestURI(), '?').get(0);
        if (PathIgnoringUtil.antMatchers(request, path)) {
            List<String> ignores = jwtProperties.getIgnores();
            if (!PathIgnoringUtil.antMatchers(request, ignores, path)) {
                String token = request.getHeader(jwtProperties.getHeader());
                try {
                    if (StrUtil.isEmpty(token)) {
                        throw new BaseRuntimeException("token不能为空");
                    }
                    Claims claims = jwtTokenUtil.parseJWT(token);
                    if (claims.getExpiration().before(DateUtil.date())) {
                        throw new BaseRuntimeException("token已过期，请重新获取");
                    }
                    request.setAttribute("identityId", claims.getSubject());//设置用户凭证id
                } catch (BaseRuntimeException runtimeException) {
                    ResultDataUtil<Object> resultDataUtil = ResultDataUtil.noAuthentication("尚未认证，请先认证");
                    JSONObject jo = (JSONObject) JSONObject.toJSON(resultDataUtil);
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpServletResponse.SC_OK);
                    PrintWriter printWriter = response.getWriter();
                    printWriter.write(jo.toJSONString());
                    printWriter.flush();
                    printWriter.close();
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
