package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.dto.system.SysLogDto;
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
    })
    public Map<String, Object> findPage(@RequestParam Integer num, @RequestParam Integer size, SysLogDto sysLogDto) {
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysLog.class, tableFieldInfo -> !"".equals(tableFieldInfo.getColumn()));
        if (sysLogDto.getOperator() != null && !"".equals(sysLogDto.getOperator()))
            queryWrapper.lambda().eq(SysLog::getOperator, sysLogDto.getOperator());
        if (sysLogDto.getLoginId() != null && !"".equals(sysLogDto.getLoginId()))
            queryWrapper.lambda().eq(SysLog::getLoginIp, sysLogDto.getLoginId());
        if (sysLogDto.getLevel() != null)
            queryWrapper.lambda().eq(SysLog::getLevel, sysLogDto.getLevel());
        if (sysLogDto.getBeginTime() != null && sysLogDto.getEndTime() != null)
            queryWrapper.lambda().between(SysLog::getCreateTime, sysLogDto.getBeginTime(), sysLogDto.getEndTime());
        Page<SysLog> page = sysLogService.page(new Page<>(num, size), queryWrapper);
        return ResponseDataUtil.ok("查询日志信息列表成功", page);
    }
}
