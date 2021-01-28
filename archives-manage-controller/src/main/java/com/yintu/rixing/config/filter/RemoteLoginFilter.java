package com.yintu.rixing.config.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: mlf
 * @Date: 2021/1/28 15:21:35
 * @Version: 1.0
 */
@WebFilter(urlPatterns = "/remote/*", filterName = "remoteLoginFilter")
@Slf4j
public class RemoteLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("....................................................................................");
    }
}
