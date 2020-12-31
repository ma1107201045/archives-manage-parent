package com.yintu.rixing.config.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yintu.rixing.config.exception.VerificationCodeException;
import com.yintu.rixing.util.ResponseDataUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2020/11/26 14:40
 * @Version: 1.0
 */
@Component
public class VerificationCodeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //判断请求页面是否为/login、该路径对应登录form表单的action路径，请求的方法是否为POST，是的话进行验证码校验逻辑，否则直接执行filterChain.doFilter让代码往下走
        String contextPath = request.getContextPath();
        if (StrUtil.equalsIgnoreCase(contextPath + "/login", request.getRequestURI())
                && StrUtil.equalsIgnoreCase("post", request.getMethod())) {
            try {
                //校验验证码 校验通过、继续向下执行   验证失败、抛出异常
                this.validateCode(request);
                //从Session移除该字段信息
                request.getSession().removeAttribute("captcha");
            } catch (VerificationCodeException e) {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                Map<String, Object> errorData = ResponseDataUtil.error(e.getMessage());
                JSONObject jo = (JSONObject) JSONObject.toJSON(errorData);
                out.write(jo.toJSONString());
                out.flush();
                out.close();
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //从Session获取保存在服务器端的验证码
        String codeInSession = (String) session.getAttribute("captcha");

        //获取表单提交的图片验证码
        String codeInRequest = request.getParameter("captcha");

        //验证码空校验
        if (StrUtil.isBlank(codeInRequest)) {
            throw new VerificationCodeException("验证码不能为空！");
        }

        //验证码校验
        if (codeInSession == null) {
            throw new VerificationCodeException("验证码不存在，请重新发送！");
        }
        //判断是否相等
        if (!StrUtil.equalsAnyIgnoreCase(codeInRequest, codeInSession)) {
            throw new VerificationCodeException("验证码不正确！");
        }

    }
}
