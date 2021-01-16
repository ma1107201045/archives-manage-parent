package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysApprovalProcessFormDto;
import com.yintu.rixing.dto.system.SysApprovalProcessQueryDto;
import com.yintu.rixing.dto.system.SysUserFormDto;
import com.yintu.rixing.dto.system.SysUserQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统审批流程表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/system/sys-approval-process")
@Api(tags = "审批流程接口")
@ApiSort(10)
public class SysApprovalProcessController extends Authenticator implements BaseController<SysApprovalProcessFormDto, SysApprovalProcessQueryDto, SysApprovalProcess, Integer> {

    @Autowired
    private ISysApprovalProcessService iSysApprovalProcessService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "添加审批流程信息")
    @PostMapping
    @ApiOperation(value = "添加审批流程信息", notes = "添加审批流程信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysApprovalProcessFormDto formDto) {
        iSysApprovalProcessService.save(formDto);
        return ResultDataUtil.ok("添加审批流程信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统设置", context = "删除审批流程信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除审批流程信息", notes = "删除审批流程信息", position = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysApprovalProcessService.removeByIds(ids);
        return ResultDataUtil.ok("删除审批流程信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = "修改审批流程信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改审批流程信息", notes = "修改审批流程信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(Integer id, @Validated SysApprovalProcessFormDto formDto) {
        iSysApprovalProcessService.updateById(formDto);
        return ResultDataUtil.ok("修改审批流程信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询审批流程单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询审批流程单条信息", notes = " 查询审批流程单条信息", position = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysApprovalProcess> findById(@PathVariable Integer id) {
        SysApprovalProcess sysApprovalProcess = iSysApprovalProcessService.getById(id);
        return ResultDataUtil.ok("查询审批流程单条信息成功", sysApprovalProcess);

    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询审批流程列表信息")
    @GetMapping
    @ApiOperation(value = "查询审批流程列表信息", notes = "查询审批流程列表信息", position = 5)
    public ResultDataUtil<Page<SysApprovalProcess>> findPage(SysApprovalProcessQueryDto queryDto) {
        Page<SysApprovalProcess> sysApprovalProcessPage = iSysApprovalProcessService.page(queryDto);
        return ResultDataUtil.ok("查询审批流程列表信息成功", sysApprovalProcessPage);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询审批流程配置树信息")
    @GetMapping("/sys-configuration")
    @ApiOperation(value = "查询审批流程配置树信息", notes = "查询审批流程配置树信息", position = 6)
    public ResultDataUtil<List<TreeUtil>> finTree() {
        List<TreeUtil> treeUtils = iSysApprovalProcessService.tree(this.getLoginUserId());
        return ResultDataUtil.ok("查询审批流程配置树信息成功", treeUtils);
    }
}
