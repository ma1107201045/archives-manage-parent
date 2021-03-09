package com.yintu.rixing.system;


import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysDepartmentFormDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统部门管理表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/system/sys-department")
@Api(tags = "部门管理接口")
@ApiSupport(order = 1)
public class SysDepartmentController extends Authenticator {
    @Autowired
    private ISysDepartmentService iSysDepartmentService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "添加部门管理信息")
    @PostMapping
    @ApiOperation(value = "添加部门管理信息", notes = "添加部门管理信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysDepartmentFormDto sysDepartmentFormDto) {
        iSysDepartmentService.save(sysDepartmentFormDto);
        return ResultDataUtil.ok("添加部门管理信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统设置", context = "删除部门管理信息")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除部门管理信息", notes = "删除部门管理信息", position = 2)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Integer id) {
        iSysDepartmentService.removeById(id);
        return ResultDataUtil.ok("删除部门管理信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = " 修改部门管理信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改部门管理信息", notes = "修改部门管理信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysDepartmentFormDto sysDepartmentFormDto) {
        iSysDepartmentService.updateById(sysDepartmentFormDto);
        return ResultDataUtil.ok("修改部门管理信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询部门管理单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询部门管理单条信息", notes = " 查询部门管理单条信息", position = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> findById(@PathVariable Integer id) {
        SysDepartment sysDepartment = iSysDepartmentService.getById(id);
        return ResultDataUtil.ok("查询部门管理单条信息成功", sysDepartment);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询部门管理列表信息树成功")
    @GetMapping
    @ApiOperation(value = "查询部门管理列表信息树", notes = "查询部门管理列表信息树", position = 5)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysDepartmentService.listTree(TreeUtil.ROOT_PARENT_ID);
        return ResultDataUtil.ok("查询部门管理列表信息树成功", treeNodeUtils);
    }
}
