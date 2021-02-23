package com.yintu.rixing.make;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.make.MakeBorrowApproveDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysApprovalProcessService;
import com.yintu.rixing.system.SysApprovalProcess;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.make.MakeBorrowTransferVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 编研和审核人中间表 前端控制器
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/MakeCompilationAroud/make-compilation-auditor")
public class MakeCompilationAuditorController extends Authenticator {
    @Autowired
    private IMakeCompilationAuditorService iMakeCompilationAuditorService;
    @Autowired
    private ISysApprovalProcessService iSysApprovalProcessService;
    @Autowired
    private IMakeBorrowService iMakeBorrowService;

    //查询审批流
    @Log(level = EnumLogLevel.TRACE, module = "利用中心", context = "查询审批流程列表信息")
    @GetMapping
    @ApiOperation(value = "查询审批流程列表信息", notes = "查询审批流程列表信息")
    public ResultDataUtil<List<SysApprovalProcess>> findPage() {
        List<SysApprovalProcess> sysApprovalProcessPage = iSysApprovalProcessService.list();
        return ResultDataUtil.ok("查询审批流程列表信息成功", sysApprovalProcessPage);
    }

    //添加审批人
    @PostMapping("/addAuditors")
    @Log(level = EnumLogLevel.INFO, module = "利用中心", context = "添加档案编研审批人信息")
    @ApiOperation(value = "添加档案编研审批人信息", notes = "添加档案编研审批人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compilationId", value = "文档编研id"),
            @ApiImplicitParam(name = "auditorId", value = "审批流id")
    })
    public ResultDataUtil<Object>addAuditors(Integer compilationId,Integer auditorId){
        iMakeCompilationAuditorService.addAuditors(compilationId,auditorId);
        return ResultDataUtil.ok("添加审批成功");
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
