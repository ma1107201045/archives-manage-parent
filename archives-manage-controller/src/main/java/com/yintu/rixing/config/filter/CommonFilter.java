package com.yintu.rixing.config.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.util.MachineCodeUtil;
import com.yintu.rixing.util.ResultDataUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: mlf
 * @Date: 2021/2/23 17:13:12
 * @Version: 1.0
 */
@WebFilter
@Component
public class CommonFilter implements Filter {

    private final String machineCode = MachineCodeUtil.getMachineCode();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = StrUtil.split(request.getRequestURI(), '?').get(0);
        if (!MachineCodeUtil.MACHINE_CODE.equals(machineCode) &&
                !(request.getContextPath() + "/captcha").equals(path)) {
            ResultDataUtil<Object> resultDataUtil = ResultDataUtil.error("机器码有误，请联系管理员");
            JSONObject jo = (JSONObject) JSONObject.toJSON(resultDataUtil);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.write(jo.toJSONString().getBytes(StandardCharsets.UTF_8));
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
        filterChain.doFilter(request, response);
    }
}
