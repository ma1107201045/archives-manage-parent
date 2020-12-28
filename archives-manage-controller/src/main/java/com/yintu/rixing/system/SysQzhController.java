package com.yintu.rixing.system;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
@Api(tags = "全宗号接口")
public class SysQzhController extends AuthenticationController implements BaseController<SysQzh, Integer> {

    @Autowired
    private ISysQzhService iSysQzhService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加全宗号信息")
    @PostMapping
    @ApiOperation(value = "添加全宗号信息", notes = "添加全宗号信息")
    public Map<String, Object> add(@Validated SysQzh sysQzh) {
        iSysQzhService.save(sysQzh);
        return ResponseDataUtil.ok("添加全宗号信息成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除全宗号信息")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除全宗号信息", notes = "删除全宗号信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> remove(@PathVariable Integer id) {
        iSysQzhService.removeById(id);
        return ResponseDataUtil.ok("删除全宗号信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改全宗号信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改全宗号信息", notes = "修改全宗号信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> edit(@PathVariable Integer id, @Validated SysQzh sysQzh) {
        iSysQzhService.updateById(sysQzh);
        return ResponseDataUtil.ok("修改全宗号信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询全宗号信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询全宗号信息", notes = " 查询全宗号单条信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> findById(@PathVariable Integer id) {
        SysQzh sysQzh = iSysQzhService.getById(id);
        return ResponseDataUtil.ok("查询全宗号信息成功", sysQzh);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询全宗号信息列表")
    @GetMapping("/findpage")
    @ApiOperation(value = "查询全宗号信息列表", notes = " 多条件查询全宗号信息分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "qzhName", value = "全宗号名称")
    })
    public Map<String, Object> findPage(@RequestParam Integer num, @RequestParam Integer size, String qzhName) {
        QueryWrapper<SysQzh> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysQzh.class, tableFieldInfo -> !"".equals(tableFieldInfo.getColumn()))
                .like(SysQzh::getQzhName, qzhName == null ? "" : qzhName);
        queryWrapper.orderByDesc("id");
        Page<SysQzh> page = iSysQzhService.page(new Page<>(num, size), queryWrapper);
        return ResponseDataUtil.ok("查询全宗号信息列表成功", page);

    }


}
