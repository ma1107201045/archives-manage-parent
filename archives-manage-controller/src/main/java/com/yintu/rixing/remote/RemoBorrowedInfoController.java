package com.yintu.rixing.remote;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.dto.make.MakeBorrowRemoteFormDto;
import com.yintu.rixing.dto.make.MakeBorrowRemoteQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.make.IMakeBorrowService;
import com.yintu.rixing.util.IdentityIdUtil;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: mlf
 * @Date: 2021/2/1 14:16:16
 * @Version: 1.0
 */
@RestController
@RequestMapping("/remote/remo-borrowed-info")
@Api(tags = "远程用户借阅接口")
@ApiSupport(order = 3)
public class RemoBorrowedInfoController {
    @Autowired
    private IMakeBorrowService iMakeBorrowService;

    @Log(level = EnumLogLevel.DEBUG, module = "远程借阅", context = "添加远程用户借阅信息")
    @PostMapping
    @ApiOperation(value = "添加远程用户借阅信息", notes = "添加远程用户借阅信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> add(HttpServletRequest request, @Validated MakeBorrowRemoteFormDto makeBorrowElectronicFormDto) {
        makeBorrowElectronicFormDto.setUserId(IdentityIdUtil.get(request));
        iMakeBorrowService.saveRemote(makeBorrowElectronicFormDto);
        return ResultDataUtil.ok("添加远程用户借阅信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "远程借阅", context = "查看我的借阅记录信息")
    @PostMapping
    @ApiOperation(value = "查看我的借阅记录信息", notes = "查看我的借阅记录信息")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<Object> findPage(HttpServletRequest request, @Validated MakeBorrowRemoteQueryDto makeBorrowRemoteQueryDto) {
        makeBorrowRemoteQueryDto.setUserId(IdentityIdUtil.get(request));
        iMakeBorrowService.pageRemote(makeBorrowRemoteQueryDto);
        return ResultDataUtil.ok("查看我的借阅记录信息成功");
    }
}
