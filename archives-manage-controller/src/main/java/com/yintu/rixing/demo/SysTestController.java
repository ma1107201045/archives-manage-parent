package com.yintu.rixing.demo;


import com.yintu.rixing.util.ResponseDataUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
@RequestMapping("/demo/demo-test")
public class SysTestController {

    @GetMapping("test")
    public Map<String, Object> test() {
        return ResponseDataUtil.ok("hello world!!");
    }
}
