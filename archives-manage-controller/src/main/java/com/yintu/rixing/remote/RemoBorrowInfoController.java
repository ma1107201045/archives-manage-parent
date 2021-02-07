package com.yintu.rixing.remote;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.dto.make.MakeBorrowRemoteFormDto;
import com.yintu.rixing.dto.make.MakeBorrowQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.make.IMakeBorrowPurposeService;
import com.yintu.rixing.make.IMakeBorrowService;
import com.yintu.rixing.make.MakeBorrowPurpose;
import com.yintu.rixing.util.IdentityIdUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.make.MakeBorrowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/2/1 14:16:16
 * @Version: 1.0
 */
@RestController
@RequestMapping("/remote/remo-borrow-info")
@Api(tags = "远程用户借阅接口")
@ApiSupport(order = 3)
public class RemoBorrowInfoController {
    @Autowired
    private IMakeBorrowService iMakeBorrowService;
    @Autowired
    private IMakeBorrowPurposeService iMakeBorrowPurposeService;

    @Log(level = EnumLogLevel.DEBUG, module = "远程借阅", context = "添加远程借阅信息")
    @PostMapping
    @ApiOperation(value = "添加远程借阅信息", notes = "添加远程借阅信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> add(HttpServletRequest request, @Validated MakeBorrowRemoteFormDto makeBorrowElectronicFormDto) {
        makeBorrowElectronicFormDto.setUserType((short) 2);
        makeBorrowElectronicFormDto.setUserId(IdentityIdUtil.get(request));
        iMakeBorrowService.saveRemote(makeBorrowElectronicFormDto);
        return ResultDataUtil.ok("添加远程借阅信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "远程借阅", context = "删除远程用户借阅信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息", position = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iMakeBorrowService.removeByIds(ids);
        return ResultDataUtil.ok("删除远程用户借阅信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "远程借阅", context = "预览远程用户借阅信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "预览远程用户借阅信息", notes = "预览远程用户借阅信息")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<String> preview(@PathVariable Integer id) {
        String requestMapping = iMakeBorrowService.preview(id);
        return ResultDataUtil.ok("预览远程用户借阅信息成功", requestMapping);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "远程借阅", context = "查看我的借阅信息")
    @GetMapping
    @ApiOperation(value = "查看我的借阅信息", notes = "查看我的借阅信息")
    @ApiOperationSupport(order = 4)
    public ResultDataUtil<Page<MakeBorrowVo>> findPage(HttpServletRequest request, @Validated MakeBorrowQueryDto makeBorrowRemoteQueryDto) {
        makeBorrowRemoteQueryDto.setUserType((short) 2);
        makeBorrowRemoteQueryDto.setUserId(IdentityIdUtil.get(request));
        Page<MakeBorrowVo> makeBorrowRemoteQueryVoPage = iMakeBorrowService.page(makeBorrowRemoteQueryDto);
        return ResultDataUtil.ok("查看我的借阅信息成功", makeBorrowRemoteQueryVoPage);
    }

    @Log(level = EnumLogLevel.TRACE, module = "远程借阅", context = "查询远程借阅利用列表信息")
    @GetMapping("/remo-borrow-purpose")
    @ApiOperation(value = "查询远程借阅利用列表信息", notes = "查询远程借阅利用列表信息")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<List<MakeBorrowPurpose>> findBorrowPurposes() {
        List<MakeBorrowPurpose> makeBorrowPurposes = iMakeBorrowPurposeService.list();
        return ResultDataUtil.ok("查询远程借阅利用列表信息成功", makeBorrowPurposes);
    }

}
