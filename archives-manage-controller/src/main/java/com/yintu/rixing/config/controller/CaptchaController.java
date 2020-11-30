package com.yintu.rixing.config.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: mlf
 * @Date: 2020/11/26 14:02
 * @Version: 1.0
 */
@Controller
@RequestMapping("/captcha")
@Api(tags = "登录有关接口")
public class CaptchaController {

    @GetMapping
    @ApiOperation(value = "获取验证码信息", notes = "获取验证码信息")
    public void getCode(HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 40, 4, 10);
        System.out.println(circleCaptcha.getCode());
        session.setAttribute("captcha", circleCaptcha.getCode());
        OutputStream os = response.getOutputStream();
        circleCaptcha.write(os);
        os.flush();
        os.close();
    }
}
