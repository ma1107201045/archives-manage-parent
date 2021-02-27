package com.yintu.rixing.person;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.make.MakeBorrowApproveDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.make.IMakeBorrowService;
import com.yintu.rixing.make.IMakeCompilationAuditorService;
import com.yintu.rixing.make.IMakeCompilationService;
import com.yintu.rixing.make.MakeCompilation;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.make.MakeBorrowTransferVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Mr.liu
 * @Date 2021/2/26 17:13
 * @Version 1.0
 * 需求:
 */
@RestController
@RequestMapping("/person/per-make-compilation")
@Api(tags = "档案编研审核")
public class PerMakeCompilationController extends Authenticator {
    @Autowired
    private IMakeCompilationService iMakeCompilationService;
    @Autowired
    private IMakeCompilationAuditorService iMakeCompilationAuditorService;
    @Autowired
    private IMakeBorrowService iMakeBorrowService;

    //待处理的档案编研
    @Log(level = EnumLogLevel.TRACE, module = "个人中心", context = "查询待处理的档案编研列表信息")
    @GetMapping("/findAllNotAudit")
    @ApiOperation(value = "查询待处理的档案编研列表信息", notes = "查询待处理的档案编研列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "topicName", value = "专题名称")
    })
    public ResultDataUtil<Object>findAllNotAudit(Integer num,Integer size,String topicName){
        Integer userId = this.getLoginUserId();
        Page<MakeCompilation> makeCompilations=iMakeCompilationService.findAllNotAudit(num,size,topicName,userId);
        return ResultDataUtil.ok("查询待处理编研成功",makeCompilations);
    }

    //已处理档案编研
    @Log(level = EnumLogLevel.TRACE, module = "个人中心", context = "查询已处理档案编研列表信息")
    @GetMapping("/findAllAlreadyAudit")
    @ApiOperation(value = "查询已处理档案编研列表信息", notes = "查询已处理档案编研列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "topicName", value = "专题名称")
    })
    public ResultDataUtil<Object>findAllAlreadyAudit(Integer num,Integer size,String topicName){
        Integer userId = this.getLoginUserId();
        Page<MakeCompilation> makeCompilations=iMakeCompilationService.findAllAlreadyAudit(num,size,topicName,userId);
        return ResultDataUtil.ok("查询已处理编研成功",makeCompilations);
    }


    //我的编研
    @Log(level = EnumLogLevel.TRACE, module = "个人中心", context = "查询我的档案编研列表信息")
    @GetMapping("/findAllMyCompilation")
    @ApiOperation(value = "查询我的档案编研列表信息", notes = "查询我的档案编研列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "topicName", value = "专题名称")
    })
    public ResultDataUtil<Object>findAllMyCompilation(Integer num,Integer size,String topicName){
        Integer userId = this.getLoginUserId();
        Page<MakeCompilation> makeCompilations=iMakeCompilationService.findAllMyCompilation(num,size,topicName,userId);
        return ResultDataUtil.ok("查询我的编研成功",makeCompilations);
    }

    //审核档案编研信息
    @Log(level = EnumLogLevel.INFO, module = "利用中心", context = "审核档案编研信息")
    @PatchMapping("/{id}")
    @ApiOperation(value = "审核档案编研信息", notes = "审核档案编研信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> approve(@Validated MakeBorrowApproveDto makeBorrowApproveDto) {
        Integer userId = this.getLoginUserId();
        makeBorrowApproveDto.setUserId(userId);
        iMakeCompilationAuditorService.approve(makeBorrowApproveDto);
        return ResultDataUtil.ok("审核档案编研信息成功");
    }

    //查询转交人员信息
    @Log(level = EnumLogLevel.TRACE, module = "利用中心", context = "查询转交用户列表信息")
    @GetMapping("/{id}/sys-user")
    @ApiOperation(value = "查询转交用户列表信息", notes = "查询转交用户列表信息")
    @ApiImplicitParam(name = "id", type = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<List<MakeBorrowTransferVo>> findUsers(@PathVariable Integer id) {
        List<MakeBorrowTransferVo> makeBorrowTransferVos = iMakeBorrowService.listTransferById(id, this.getLoginUserId());
        return ResultDataUtil.ok("查询转交用户列表信息成功", makeBorrowTransferVos);
    }


}
