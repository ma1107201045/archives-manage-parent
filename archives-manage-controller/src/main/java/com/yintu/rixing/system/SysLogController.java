package com.yintu.rixing.system;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/system/sys-log")
@Api(tags = "日志接口")
public class SysLogController extends AuthenticationController {

    @Autowired
    private ISysLogService sysLogService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询日志信息列表")
    @GetMapping
    @ApiOperation(value = "查询日志信息列表", notes = " 多条件查询日志信息分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, dataType = "int", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "operator", value = "登录名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "loginId", value = "登录ip", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "level", value = "日志级别", dataType = "short", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始日期", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束日期", dataType = "date", paramType = "query")
    })
    public Map<String, Object> findPage(@RequestParam Integer num, @RequestParam Integer size,
                                        String operator, String loginId, Short level, Date beginTime, Date endTime) {
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysLog.class, tableFieldInfo -> !"".equals(tableFieldInfo.getColumn()));
        if (operator != null && !"".equals(operator))
            queryWrapper.lambda().eq(SysLog::getOperator, operator);
        if (loginId != null && !"".equals(loginId))
            queryWrapper.lambda().eq(SysLog::getLoginIp, loginId);
        if (level != null)
            queryWrapper.lambda().eq(SysLog::getLevel, level);
        if (beginTime != null && endTime != null)
            queryWrapper.lambda().between(SysLog::getCreateTime, beginTime, endTime);
        Page<SysLog> page = sysLogService.page(new Page<>(num, size), queryWrapper);
        return ResponseDataUtil.ok("查询日志信息列表成功", page);
    }
}
