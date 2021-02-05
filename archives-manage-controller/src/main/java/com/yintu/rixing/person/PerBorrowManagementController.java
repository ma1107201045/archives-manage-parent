package com.yintu.rixing.person;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.make.MakeBorrowApproveDto;
import com.yintu.rixing.dto.person.PerBorrowManagementQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.make.IMakeBorrowService;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.make.MakeBorrowTransferVo;
import com.yintu.rixing.vo.person.PerBorrowManagementVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/5 15:14:34
 * @Version: 1.0
 */
@RestController
@RequestMapping("/person/per-borrow-management")
@Api(tags = "借阅管理接口")
@ApiSupport(order = 1)
public class PerBorrowManagementController extends Authenticator {
    @Autowired
    private IMakeBorrowService iMakeBorrowService;

    @Log(level = EnumLogLevel.INFO, module = "个人中心", context = "审核借阅管理信息")
    @PatchMapping("/{id}")
    @ApiOperation(value = "审核借阅管理信息", notes = "审核借阅管理信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> approve(@Validated MakeBorrowApproveDto makeBorrowApproveDto) {
        iMakeBorrowService.approve(makeBorrowApproveDto);
        return ResultDataUtil.ok("审核借阅管理信息成功");
    }


    @Log(level = EnumLogLevel.TRACE, module = "个人中心", context = "查询借阅管理列表信息")
    @GetMapping
    @ApiOperation(value = "查询借阅管理列表信息", notes = "查询借阅管理列表信息")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<List<PerBorrowManagementVo>> page(@Validated PerBorrowManagementQueryDto perBorrowManagementQueryDto) {
        perBorrowManagementQueryDto = null;
        List<PerBorrowManagementVo> perBorrowManagementVos = new ArrayList<>();
        return ResultDataUtil.ok("查询借阅管理列表信息成功", perBorrowManagementVos);
    }


    @Log(level = EnumLogLevel.TRACE, module = "个人中心", context = "查询转交用户列表信息")
    @GetMapping("/{id}/sys-user")
    @ApiOperation(value = "查询转交用户列表信息", notes = "查询转交用户列表信息")
    @ApiImplicitParam(name = "id", type = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<List<MakeBorrowTransferVo>> findUsers(@PathVariable Integer id) {
        List<MakeBorrowTransferVo> makeBorrowTransferVos = iMakeBorrowService.listTransferById(id, this.getLoginUserId());
        return ResultDataUtil.ok("查询转交用户列表信息成功", makeBorrowTransferVos);
    }


}
