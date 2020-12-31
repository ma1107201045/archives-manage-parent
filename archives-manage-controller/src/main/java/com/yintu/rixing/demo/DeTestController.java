package com.yintu.rixing.demo;


import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "测试接口")
public class DeTestController {

    @GetMapping("/test")
    public Map<String, Object> test() {
        return ResponseDataUtil.ok("hello world!!");
    }

    @GetMapping("/test1")
    public ResultDataUtil<SysUser> test1() {
        return ResultDataUtil.ok("", new SysUser());
    }
}
