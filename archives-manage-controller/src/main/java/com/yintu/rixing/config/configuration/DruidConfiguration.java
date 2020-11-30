package com.yintu.rixing.config.configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfiguration {
    //配置druid的监控
    //1.配置一个Web监控的filter
    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("enabled", "true");
        initParameters.put("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParameters);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterRegistrationBean;
    }

    //2.配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean<StatViewServlet> servletRegistrationBean() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("loginUsername", "super");
        initParameters.put("loginPassword", "123456");
        initParameters.put("allow", "");//默认允许所有访问
        initParameters.put("deny", "192.168.1.5");
        initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

}
