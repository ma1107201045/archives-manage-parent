package com.yintu.rixing.system;


import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.dto.system.SysDepartmentFormDto;
import com.yintu.rixing.dto.system.SysTemplateLibraryFormDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
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
 * 系统模板库表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@RestController
@RequestMapping("/system/sys-template-library")
@ApiSort(6)
@Api(tags = "模块库接口")
public class SysTemplateLibraryController extends AuthenticationController {

    @Autowired
    private ISysTemplateLibraryService iSysTemplateLibraryService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加模板库信息")
    @PostMapping
    @ApiOperation(value = "添加模板库信息", notes = "添加模板库信息")
    public Map<String, Object> add(@Validated SysTemplateLibraryFormDto sysDepartmentFormDto) {
        iSysTemplateLibraryService.save(sysDepartmentFormDto);
        return ResponseDataUtil.ok("添加模板库信息成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除模板库信息")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除模板库信息", notes = "删除模板库信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> remove(@PathVariable Integer id) {
        iSysTemplateLibraryService.removeById(id);
        return ResponseDataUtil.ok("删除模板库信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改模板库信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改模板库信息", notes = "修改模板库信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public Map<String, Object> edit(@PathVariable Integer id, @Validated SysTemplateLibraryFormDto sysTemplateLibraryFormDto) {
        iSysTemplateLibraryService.updateById(sysTemplateLibraryFormDto);
        return ResponseDataUtil.ok("修改模板库信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询部门单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询部门单条信息", notes = " 查询部门单条信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
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
