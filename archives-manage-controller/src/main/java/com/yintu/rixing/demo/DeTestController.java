package com.yintu.rixing.demo;


import com.yintu.rixing.config.jwt.JwtProperties;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/demo/de-test")
@Api(tags = "测试有关接口")
@Slf4j
public class DeTestController {
    @Autowired
    private JwtProperties jwtProperties;

    @GetMapping("/test")
    public Map<String, Object> test() {
        return ResponseDataUtil.ok("hello world!!");
    }

    @GetMapping("/test1")
    public ResultDataUtil<SysUser> test1() {
        return ResultDataUtil.ok("", new SysUser());
    }

    @GetMapping("/test2/{archivesId}")
    public ResultDataUtil<Map<String, Object>> test2(@PathVariable Integer archivesId, @RequestParam Map<String, Object> params) {
        return ResultDataUtil.ok("", null);
    }

    @GetMapping("/test3")
    public ResultDataUtil<Map<String, Object>> test3(HttpServletRequest request) {
        log.debug("getRemoteHost:" + request.getRemoteHost());
        log.debug("getRemotePort:" + request.getRemotePort());
        log.debug("getRemoteAddr:" + request.getRemoteAddr());
        log.debug("getRemoteUser:" + request.getRemoteUser());
        log.debug("-------------------------------------");
        log.debug("getScheme:" + request.getScheme());
        log.debug("getServerName:" + request.getServerName());
        log.debug("getServerPort:" + request.getServerPort());
        log.debug("getRequestURL:" + request.getRequestURL());
        log.debug("getRequestURI:" + request.getRequestURI());
        log.debug("isSecure:" + request.isSecure());
        log.debug("-------------------------------------");
        return ResultDataUtil.ok("", null);
    }

    @PostMapping("/test4")
    public ResultDataUtil<Map<String, Object>> test4(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return null;
    }
}
