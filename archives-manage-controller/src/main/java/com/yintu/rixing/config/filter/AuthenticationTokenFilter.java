package com.yintu.rixing.config.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yintu.rixing.config.jwt.JwtProperties;
import com.yintu.rixing.config.jwt.JwtTokenUtil;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.util.PathIgnoringUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = StrUtil.split(request.getRequestURL(), '?').get(0);
        if (PathIgnoringUtil.antMatchers(request, path)) {
            JwtProperties props = jwtTokenUtil.getJwtProperties();
            List<String> ignores = props.getIgnores();
            if (!PathIgnoringUtil.antMatchers(request, ignores, path)) {
                String token = request.getHeader(props.getHeader());
                if (StrUtil.isEmpty(token)) {
                    throw new BaseRuntimeException("token不能为空");
                }
                Claims claims = jwtTokenUtil.parseJWT(token);
                if (claims.getExpiration().before(DateUtil.date())) {
                    throw new BaseRuntimeException("token已过期，请重新获取");
                }
                request.setAttribute("identityId", claims.getSubject());//设置用户凭证id
            }
        }
        filterChain.doFilter(request, response);
    }
}
