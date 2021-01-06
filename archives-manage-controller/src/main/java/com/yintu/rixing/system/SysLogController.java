package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.controller.Authenticator;
import com.yintu.rixing.dto.system.SysLogDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@ApiSort(5)
@Api(tags = "日志接口")
public class SysLogController extends Authenticator {

    @Autowired
    private ISysLogService iSysLogService;

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", description = "查询日志列表信息")
    @GetMapping
    @ApiOperation(value = "查询日志列表信息", notes = " 查询日志列表信息")
    public ResultDataUtil<Page<SysLog>> findPage(@Validated SysLogDto sysLogDto) {
        Page<SysLog> page = iSysLogService.page(sysLogDto);
        return ResultDataUtil.ok("查询日志列表信息成功", page);
    }
}
