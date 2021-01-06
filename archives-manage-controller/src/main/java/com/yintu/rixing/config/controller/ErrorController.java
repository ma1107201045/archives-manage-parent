package com.yintu.rixing.config.controller;

/**
 * @Author: mlf
 * @Date: 2020/12/7 21:18
 * @Version: 1.0
 */

import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/error")
public class ErrorController {
    @Autowired
    private BasicErrorController basicErrorController;

    /**
     * @param request  请求对象
     * @param response 返回对象
     * @return 默认错误异常处理
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResultDataUtil<Object> error(HttpServletRequest request, HttpServletResponse response) {
        //定义为正常返回
        response.setStatus(HttpStatus.OK.value());
        //获取异常返回
        ResponseEntity<Map<String, Object>> errorDetail = basicErrorController.error(request);
        //自行组织返回数据
        return ResultDataUtil.error(errorDetail.getStatusCode().toString());
    }
}
