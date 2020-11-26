package com.yintu.rixing.component;

import com.alibaba.fastjson.JSONObject;
import com.yintu.rixing.exception.VerificationCodeException;
import com.yintu.rixing.filter.VerificationCodeFilter;
import com.yintu.rixing.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

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
            PrintWriter out = response.getWriter();
            JSONObject jo = (JSONObject) JSONObject.toJSON(ResponseDataUtil.ok("登录成功", authenticationException.getPrincipal()));
            out.write(jo.toJSONString());
            out.flush();
            out.close();
        }).permitAll().failureHandler((request, response, authenticationException) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map<String, Object> errorData;
            if (authenticationException instanceof BadCredentialsException) {
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
            } else {
                errorData = ResponseDataUtil.error("登录异常");
            }
            JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
            out.write(jo.toJSONString());
            out.flush();
            out.close();
        }).permitAll()
                .and().rememberMe().userDetailsService(userDetailsService).tokenValiditySeconds(60 * 60 * 24 * 365).rememberMeParameter("rememberMe").rememberMeCookieName("REMEMBERME")
                .and().logout().logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpServletResponse.SC_OK);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> errorData = ResponseDataUtil.ok("注销成功");
                    JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
                    out.write(jo.toJSONString());
                    out.flush();
                    out.close();
                }).permitAll()
                .and().httpBasic().authenticationEntryPoint((request, response, authenticationException) -> { //没有登录权限时，在这里处理结果，不要重定向
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map<String, Object> errorData = ResponseDataUtil.noLogin(authenticationException.getMessage());
            JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
            out.write(jo.toJSONString());
            out.flush();
            out.close();
        })
                .and().exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {  //没有访问权限时，在这里处理结果，不要重定向
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map<String, Object> errorData = ResponseDataUtil.noAuthorize(accessDeniedException.getMessage());
            JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
            out.write(jo.toJSONString());
            out.flush();
            out.close();
        }).and().sessionManagement().maximumSessions(1).expiredSessionStrategy(event -> {
            HttpServletResponse response = event.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map<String, Object> errorData = ResponseDataUtil.error("您已在另一台设备登录，本次登录已下线");
            JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
            out.write(jo.toJSONString());
            out.flush();
            out.close();
        });
        //开启跨域访问
        http.cors().disable();
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        http.csrf().disable();
    }

    /**
     * 忽略不用认证的url
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").mvcMatchers("/code/**");
    }
}
