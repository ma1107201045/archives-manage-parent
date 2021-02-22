package com.yintu.rixing.make;


import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysApprovalProcessService;
import com.yintu.rixing.system.SysApprovalProcess;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class MakeCompilationAuditorController {

    @Autowired
    private ISysApprovalProcessService iSysApprovalProcessService;

    //查询审批流
    @Log(level = EnumLogLevel.TRACE, module = "借阅申请", context = "查询审批流程列表信息")
    @GetMapping
    @ApiOperation(value = "查询审批流程列表信息", notes = "查询审批流程列表信息")
    public ResultDataUtil<List<SysApprovalProcess>> findPage() {
        List<SysApprovalProcess> sysApprovalProcessPage = iSysApprovalProcessService.list();
        return ResultDataUtil.ok("查询审批流程列表信息成功", sysApprovalProcessPage);
    }




}
