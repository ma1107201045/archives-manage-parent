package com.yintu.rixing.system;


import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.dto.system.SysDepartmentFormDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeNodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统部门表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/system/sys-department")
@Api(tags = "部门接口（组织机构接口）")
@ApiSort(4)
public class SysDepartmentController extends AuthenticationController {
    @Autowired
    private ISysDepartmentService iSysDepartmentService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加部门信息")
    @PostMapping
    @ApiOperation(value = "添加部门信息", notes = "添加部门信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysDepartmentFormDto sysDepartmentFormDto) {
        iSysDepartmentService.save(sysDepartmentFormDto);
        return ResultDataUtil.ok("添加部门信息成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除部门信息")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除部门信息", notes = "删除部门信息", position = 2)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Integer id) {
        iSysDepartmentService.removeById(id);
        return ResultDataUtil.ok("删除部门信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改部门信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改部门信息", notes = "修改部门信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysDepartmentFormDto sysDepartmentFormDto) {
        iSysDepartmentService.updateById(sysDepartmentFormDto);
        return ResultDataUtil.ok("修改部门信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询部门单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询部门单条信息", notes = " 查询部门单条信息", position = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> findById(@PathVariable Integer id) {
        SysDepartment sysDepartment = iSysDepartmentService.getById(id);
        return ResultDataUtil.ok("查询部门单条信息成功", sysDepartment);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询部门列表信息树成功")
    @GetMapping
    @ApiOperation(value = "查询部门列表信息树", notes = "查询部门列表信息树", position = 5)
    public ResultDataUtil<List<TreeNodeUtil>> findTree() {
        List<TreeNodeUtil> treeNodeUtils = iSysDepartmentService.listTree(-1);
        return ResultDataUtil.ok("查询部门列表信息树成功", treeNodeUtils);
    }
}
