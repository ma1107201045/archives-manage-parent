package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysRemoteBorrowedInfoQueryDto;
import com.yintu.rixing.dto.system.SysRemoteUserQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 系统远程借阅记录表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2021-02-01
 */
@RestController
@RequestMapping("/system/sys-remote-borrowed-info")
@Api(tags = "远程用户借阅记录接口")
@ApiSort(12)
public class SysRemoteBorrowedInfoController extends Authenticator {

    @Autowired
    private ISysRemoteBorrowedInfoService iSysRemoteBorrowedInfoService;

    @Log(level = EnumLogLevel.WARN, module = "系统设置", context = "删除远程用户借阅记录信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除远程用户借阅记录信息", notes = "删除远程用户借阅记录信息")
    @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysRemoteBorrowedInfoService.removeByIds(ids);
        return ResultDataUtil.ok("删除远程用户借阅记录信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询远程用户借阅记录信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询远程用户借阅记录单条信息", notes = " 查询远程用户借阅记录单条信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<SysRemoteBorrowedInfo> findById(@PathVariable Integer id) {
        SysRemoteBorrowedInfo sysRemoteBorrowedInfo = iSysRemoteBorrowedInfoService.getById(id);
        return ResultDataUtil.ok("查询远程用户借阅记录单条信息成功", sysRemoteBorrowedInfo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询远程用户借阅记录列表信息")
    @GetMapping
    @ApiOperation(value = "查询远程用户借阅记录列表信息", notes = "查询远程用户借阅记录列表信息")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<Page<SysRemoteBorrowedInfo>> findPage(@Validated SysRemoteBorrowedInfoQueryDto sysRemoteBorrowedInfoQueryDto) {
        Page<SysRemoteBorrowedInfo> page = iSysRemoteBorrowedInfoService.page(sysRemoteBorrowedInfoQueryDto);
        return ResultDataUtil.ok("查询远程用户借阅记录列表信息成功", page);
    }
}
