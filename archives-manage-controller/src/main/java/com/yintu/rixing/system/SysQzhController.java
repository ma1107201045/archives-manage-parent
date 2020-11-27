package com.yintu.rixing.system;


import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-11-27
 */
@RestController
@RequestMapping("/system/sys-qzh")
public class SysQzhController extends AuthenticationController implements BaseController<SysQzh, Integer> {
    @Autowired
    private ISysQzhService iSysQzhService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加全宗号信息")
    @PostMapping
    @Override
    public Map<String, Object> add(@Validated SysQzh entity) {
       // iSysQzhService.save(entity);
        return ResponseDataUtil.ok("添加全宗号信息成功");
    }

    @Override
    public Map<String, Object> remove(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> edit(Integer id, SysQzh entity) {
        return null;
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        return null;
    }
}
