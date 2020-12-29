package com.yintu.rixing.system;


import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.dto.system.SysDepartmentFormDto;
import com.yintu.rixing.dto.system.SysPermissionFormDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.TreeNodeUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/system/sys-department")
public class SysDepartmentController extends AuthenticationController implements BaseController<SysDepartmentFormDto, Integer> {
    @Autowired
    private ISysDepartmentService iSysDepartmentService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加部门信息")
    @PostMapping
    @ApiOperation(value = "添加部门信息", notes = "添加部门信息")
    public Map<String, Object> add(@Validated SysDepartmentFormDto sysDepartmentFormDto) {
        iSysDepartmentService.save(sysDepartmentFormDto);
        return ResponseDataUtil.ok("添加部门信息成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除部门信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除部门信息", notes = "删除部门信息")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true, paramType = "path")
    public Map<String, Object> remove(@PathVariable Set<Integer> ids) {
        iSysDepartmentService.removeByIds(ids);
        return ResponseDataUtil.ok("删除部门信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改部门信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改部门信息", notes = "修改部门信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> edit(@PathVariable Integer id, @Validated SysDepartmentFormDto sysDepartmentFormDto) {
        iSysDepartmentService.updateById(sysDepartmentFormDto);
        return ResponseDataUtil.ok("修改部门信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询部门单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询部门单条信息", notes = " 查询部门单条信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> findById(@PathVariable Integer id) {
        SysDepartment sysDepartment = iSysDepartmentService.getById(id);
        return ResponseDataUtil.ok("查询部门单条信息成功", sysDepartment);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询部门列表信息树成功")
    @GetMapping
    @ApiOperation(value = "查询部门列表信息树", notes = "查询部门列表信息树")
    public Map<String, Object> findTree() {
        List<TreeNodeUtil> treeNodeUtils = iSysDepartmentService.listTree(-1);
        return ResponseDataUtil.ok("查询部门列表信息树成功", treeNodeUtils);
    }
}
