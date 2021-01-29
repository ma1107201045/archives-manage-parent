package com.yintu.rixing.security;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.security.SecLogQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 安全日志表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/security/sec-log")
@Api(tags = "日志接口")
@ApiSort(1)
public class SecLogController extends Authenticator {

    @Autowired
    private ISecLogService iSysLogService;

    @Log(level = EnumLogLevel.TRACE, module = "安全中心", context = "查询日志列表信息")
    @GetMapping
    @ApiOperation(value = "查询日志列表信息", notes = " 查询日志列表信息")
    public ResultDataUtil<Page<SecLog>> findPage(@Validated SecLogQueryDto secLogDto) {
        Page<SecLog> page = iSysLogService.page(secLogDto);
        return ResultDataUtil.ok("查询日志列表信息成功", page);
    }
}
