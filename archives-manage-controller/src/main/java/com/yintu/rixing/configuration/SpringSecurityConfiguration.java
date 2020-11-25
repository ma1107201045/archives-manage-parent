package com.yintu.rixing.configuration;

import com.alibaba.fastjson.JSONObject;
import com.yintu.rixing.exception.VerificationCodeException;
import com.yintu.rixing.system.ISysTestService;
import com.yintu.rixing.system.ISysUserService;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2020/11/25 14:40
 * @Version: 1.0
 */
@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private ISysUserService iSysUserService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider()).httpBasic()
                .and().authorizeRequests().anyRequest().authenticated() //必须授权才能范围
                .and().formLogin().permitAll().successHandler((request, response, authenticationException) -> {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            JSONObject jo = (JSONObject) JSONObject.toJSON(ResponseDataUtil.ok("登录成功", authenticationException.getPrincipal()));
            out.write(jo.toJSONString());
            out.flush();
            out.close();
        }).failureHandler((request, response, authenticationException) -> {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map<String, Object> errorData = ResponseDataUtil.error(authenticationException.getMessage());
            if (authenticationException instanceof VerificationCodeException) {
                errorData = ResponseDataUtil.error("验证码不正确");
            } else if (authenticationException instanceof BadCredentialsException) {
                errorData = ResponseDataUtil.error("用户名或者密码输入错误，请重新输入");
            } else if (authenticationException instanceof DisabledException) {
                errorData = ResponseDataUtil.error("账户被禁用，请联系管理员");
            } else if (authenticationException instanceof LockedException) {
                errorData = ResponseDataUtil.error("账户被锁定，请联系管理员");
            } else if (authenticationException instanceof CredentialsExpiredException) {
                errorData = ResponseDataUtil.error("密码过期，请联系管理员");
            } else if (authenticationException instanceof AccountExpiredException) {
                errorData = ResponseDataUtil.error("账户过期，请联系管理员");
            } else if (authenticationException instanceof AuthenticationServiceException) {
                errorData = ResponseDataUtil.error(authenticationException.getMessage());
            }
            JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
            out.write(jo.toJSONString());
            out.flush();
            out.close();
        })
                .and().logout().logoutSuccessHandler((request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map<String, Object> errorData = ResponseDataUtil.ok("注销成功");
            JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
            out.write(jo.toJSONString());
            out.flush();
            out.close();
        }).permitAll()
                .and().exceptionHandling().authenticationEntryPoint((request, response, authenticationException) -> { //没有登录权限时，在这里处理结果，不要重定向
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map<String, Object> errorData = ResponseDataUtil.noAuthorize(authenticationException.getMessage());
            JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
            out.write(jo.toJSONString());
            out.flush();
            out.close();
        }).accessDeniedHandler((request, response, accessDeniedException) -> {  //没有访问权限时，在这里处理结果，不要重定向
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map<String, Object> errorData = ResponseDataUtil.noAuthorize(accessDeniedException.getMessage());
            JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
            out.write(jo.toJSONString());
            out.flush();
            out.close();
        })
                .and().cors().disable() //开启跨域访问
                .csrf().disable();  //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(iSysUserService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

}
