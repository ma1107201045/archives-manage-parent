package com.yintu.rixing.system;


import cn.hutool.core.date.DateUtil;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        String username = this.getLoginUserName();
        Date now = DateUtil.date();
        entity.setCreateBy(username);
        entity.setCreateTime(now);
        entity.setModifiedBy(username);
        entity.setModifiedTime(now);
        iSysQzhService.save(entity);
        return ResponseDataUtil.ok("添加全宗号信息成功", entity);
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除全宗号信息")
    @DeleteMapping("/{id}")
    @Override
    public Map<String, Object> remove(@PathVariable Integer id) {
        iSysQzhService.removeById(id);
        return ResponseDataUtil.ok("删除全宗号信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改全宗号信息")
    @PutMapping("/{id}")
    @Override
    public Map<String, Object> edit(@PathVariable Integer id, @Validated SysQzh entity) {
        String username = this.getLoginUserName();
        Date now = DateUtil.date();
        entity.setModifiedBy(username);
        entity.setModifiedTime(now);
        iSysQzhService.updateById(entity);
        return ResponseDataUtil.ok("修改全宗号信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询全宗号信息")
    @GetMapping("/{id}")
    @Override
    public Map<String, Object> findById(@PathVariable Integer id) {
        SysQzh sysQzh = iSysQzhService.getById(id);
        return ResponseDataUtil.ok("查询单条全宗号信息成功", sysQzh);
    }
}
