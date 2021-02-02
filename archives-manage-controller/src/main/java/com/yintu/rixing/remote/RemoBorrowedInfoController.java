package com.yintu.rixing.remote;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.dto.system.SysRemoteBorrowedInfoFormDto;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysRemoteBorrowedInfoService;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author: mlf
 * @Date: 2021/2/1 14:16:16
 * @Version: 1.0
 */
@RestController
@RequestMapping("/remote/remo-borrowed-info")
@Api(tags = "远程用户借阅接口")
@ApiSort(2)
@ApiIgnore
public class RemoBorrowedInfoController {
    @Autowired
    private ISysRemoteBorrowedInfoService iSysRemoteBorrowedInfoService;

    @Log(level = EnumLogLevel.DEBUG, module = "远程借阅", context = "添加远程用户借阅信息")
    @PostMapping
    @ApiOperation(value = "添加远程用户借阅信息", notes = "添加远程用户借阅信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> add(@Validated SysRemoteBorrowedInfoFormDto sysRemoteBorrowedInfoFormDto) {
        iSysRemoteBorrowedInfoService.save(sysRemoteBorrowedInfoFormDto);
        return ResultDataUtil.ok("添加远程用户借阅信息成功");
    }
}
