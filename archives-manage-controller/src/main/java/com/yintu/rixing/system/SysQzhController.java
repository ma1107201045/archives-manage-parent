package com.yintu.rixing.system;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.demo.SysTest;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.*;
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
    @ApiOperation(value = "添加全宗号信息", httpMethod = "POST", response = Map.class)
    public Map<String, Object> add(@Validated @ApiParam("参数") SysQzh entity) {
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
    @ApiOperation(value = "删除全宗号信息", httpMethod = "DELETE", response = Map.class)
    public Map<String, Object> remove(@PathVariable Integer id) {
        iSysQzhService.removeById(id);
        return ResponseDataUtil.ok("删除全宗号信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改全宗号信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改全宗号信息", httpMethod = "PUT", response = Map.class)
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
    @ApiOperation(value = "查询全宗号信息", httpMethod = "GET", response = Map.class)
    public Map<String, Object> findById(@PathVariable Integer id) {
        SysQzh sysQzh = iSysQzhService.getById(id);
        return ResponseDataUtil.ok("查询单条全宗号信息成功", sysQzh);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询全宗号信息列表")
    @GetMapping
    @ApiOperation(value = "查询全宗号信息列表", httpMethod = "GET", response = Map.class)
    public Map<String, Object> findPage(@RequestParam @ApiParam("页码") Integer num, @RequestParam @ApiParam("页数") Integer size, @ApiParam("全宗号名称") String qzhName) {
        QueryWrapper<SysQzh> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysQzh.class, tableFieldInfo -> !"".equals(tableFieldInfo.getColumn()))
                .like(SysQzh::getQzhName, qzhName);
        Page<SysQzh> page = iSysQzhService.page(new Page<>(num, size), queryWrapper);
        return ResponseDataUtil.ok("查询单条全宗号信息成功", page);
    }
}
