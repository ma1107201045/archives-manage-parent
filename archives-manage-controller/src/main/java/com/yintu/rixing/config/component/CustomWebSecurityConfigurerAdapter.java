package com.yintu.rixing.config.component;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.yintu.rixing.config.filter.VerificationCodeFilter;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.security.ISecLogService;
import com.yintu.rixing.security.SecLog;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.IPUtil;
import com.yintu.rixing.util.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Author: mlf
 * @Date: 2020/11/26 10:33
 * @Version: 1.0
 */
@Component
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    @Autowired
    private VerificationCodeFilter verificationCodeFilter;

    @Autowired
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Autowired
    private AccessDecisionManager accessDecisionManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ISecLogService isecLogService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                object.setAccessDecisionManager(accessDecisionManager);
                return object;
            }
        }).anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/login").successHandler((request, response, authenticationException) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            JSONObject jo = (JSONObject) JSONObject.toJSON(ResultDataUtil.ok("登录成功", authenticationException.getPrincipal()));
            servletOutputStream.write(jo.toJSONString().getBytes(StandardCharsets.UTF_8));
            servletOutputStream.flush();
            servletOutputStream.close();
            //登录日志
            SysUser sysUser = Authenticator.getPrincipal();
            SecLog secLog = new SecLog();
            secLog.setUserId(sysUser == null ? -1 : sysUser.getId());
            secLog.setUsername(sysUser == null ? "unknown" : sysUser.getUsername());
            secLog.setOperator(sysUser == null ? "unknown" : sysUser.getNickname());
            secLog.setLevel((short) 1);
            secLog.setModule("登录");
            secLog.setCreateTime(DateUtil.date());
            secLog.setContext("登录系统");
            secLog.setLoginIp(IPUtil.getIpAddress(request));
            isecLogService.save(secLog);
        }).permitAll().failureHandler((request, response, authenticationException) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            ResultDataUtil<Object> resultDataUtil;
            if (authenticationException instanceof BadCredentialsException) {
                resultDataUtil = ResultDataUtil.error("用户名或者密码输入错误，请重新输入");
            } else if (authenticationException instanceof DisabledException) {
                resultDataUtil = ResultDataUtil.error("账户被禁用，请联系管理员");
            } else if (authenticationException instanceof LockedException) {
                resultDataUtil = ResultDataUtil.error("账户被锁定，请联系管理员");
            } else if (authenticationException instanceof CredentialsExpiredException) {
                resultDataUtil = ResultDataUtil.error("密码过期，请联系管理员");
            } else if (authenticationException instanceof AccountExpiredException) {
                resultDataUtil = ResultDataUtil.error("账户过期，请联系管理员");
            } else if (authenticationException instanceof AuthenticationServiceException) {
                resultDataUtil = ResultDataUtil.noAuthentication(authenticationException.getMessage());
            } else {
                resultDataUtil = ResultDataUtil.error("登录异常");
            }
            JSONObject jo = (JSONObject) JSONObject.toJSON(resultDataUtil);
            servletOutputStream.write(jo.toJSONString().getBytes(StandardCharsets.UTF_8));
            servletOutputStream.flush();
            servletOutputStream.close();
        }).permitAll()
                .and().rememberMe().userDetailsService(userDetailsService).tokenValiditySeconds(60 * 60 * 24 * 365).rememberMeParameter("rememberMe").rememberMeCookieName("REMEMBERME")
                .and().logout().logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpServletResponse.SC_OK);
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    ResultDataUtil<Object> resultDataUtil = ResultDataUtil.ok("注销成功");
                    JSONObject jo = (JSONObject) JSONObject.toJSON(resultDataUtil);
                    servletOutputStream.write(jo.toJSONString().getBytes(StandardCharsets.UTF_8));
                    servletOutputStream.flush();
                    servletOutputStream.close();

                    //注销日志
                    SysUser sysUser = Authenticator.getPrincipal();
                    SecLog secLog = new SecLog();
                    secLog.setUserId(sysUser == null ? -1 : sysUser.getId());
                    secLog.setUsername(sysUser == null ? "unknown" : sysUser.getUsername());
                    secLog.setOperator(sysUser == null ? "unknown" : sysUser.getNickname());
                    secLog.setLevel((short) 1);
                    secLog.setModule("登录");
                    secLog.setCreateTime(DateUtil.date());
                    secLog.setContext("注销系统");
                    secLog.setLoginIp(IPUtil.getIpAddress(request));
                    isecLogService.save(secLog);
                }).permitAll()
//                .and().httpBasic().authenticationEntryPoint((request, response, authenticationException) -> { //没有登录权限时，在这里处理结果，不要重定向
//            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//            response.setStatus(HttpServletResponse.SC_OK);
//            PrintWriter out = response.getOutputStream();
//            Map<String, Object> errorData = ResponseDataUtil.noLogin(authenticationException.getMessage());
//            JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
//            out.write(jo.toJSONString());
//            out.flush();
//            out.close();
//        })
                .and().exceptionHandling()
                .authenticationEntryPoint((request, response, authenticationException) -> { //没有登录权限时，在这里处理结果，不要重定向
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpServletResponse.SC_OK);
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    ResultDataUtil<Object> resultDataUtil = ResultDataUtil.noAuthentication(authenticationException.getMessage());
                    JSONObject jo = (JSONObject) JSONObject.toJSON(resultDataUtil);
                    servletOutputStream.write(jo.toJSONString().getBytes(StandardCharsets.UTF_8));
                    servletOutputStream.flush();
                    servletOutputStream.close();
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {  //没有访问权限时，在这里处理结果，不要重定向
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpServletResponse.SC_OK);
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    ResultDataUtil<Object> resultDataUtil = ResultDataUtil.noAuthorization(accessDeniedException.getMessage());
                    JSONObject jo = (JSONObject) JSONObject.toJSON(resultDataUtil);
                    servletOutputStream.write(jo.toJSONString().getBytes(StandardCharsets.UTF_8));
                    servletOutputStream.flush();
                    servletOutputStream.close();
                }).and().sessionManagement().maximumSessions(1).expiredSessionStrategy(event -> {//用户登录踢出上一个相同用户
            HttpServletResponse response = event.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            ResultDataUtil<Object> resultDataUtil = ResultDataUtil.noAuthentication("您已在另一台设备登录，本次登录已下线");
            JSONObject jo = (JSONObject) JSONObject.toJSON(resultDataUtil);
            servletOutputStream.write(jo.toJSONString().getBytes(StandardCharsets.UTF_8));
            servletOutputStream.flush();
            servletOutputStream.close();
        });
        //开启跨域访问
        http.cors();
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        http.csrf().disable();
    }

    /**
     * 忽略不用认证的url
     *
     * @param web web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/captcha")//忽略验证码接口
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs", "/webjars/**", "/doc.html")//忽略API文档接口
                .antMatchers("/druid/**")//忽略druid接口
                .antMatchers("/remote/**");//忽略远程登录接口

    }
}
