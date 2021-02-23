package com.yintu.rixing.config.filter;

import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.util.MachineCodeUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: mlf
 * @Date: 2021/2/23 17:13:12
 * @Version: 1.0
 */
@WebFilter(urlPatterns = "/remote/*")
@Component
public class CommonFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (MachineCodeUtil.MACHINE_CODE.equals(MachineCodeUtil.getMachineCode())) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        throw new BaseRuntimeException("机器码有误，请联系管理员");
    }
}
