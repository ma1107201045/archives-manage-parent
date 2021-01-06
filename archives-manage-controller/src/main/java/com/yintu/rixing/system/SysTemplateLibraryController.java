package com.yintu.rixing.system;


import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.controller.Authenticator;
import com.yintu.rixing.dto.system.SysTemplateLibraryFormDto;
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
public class SysTemplateLibraryController extends Authenticator {

    @Autowired
    private ISysTemplateLibraryService iSysTemplateLibraryService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "添加模板库信息")
    @PostMapping
    @ApiOperation(value = "添加模板库信息", notes = "添加模板库信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysTemplateLibraryFormDto sysDepartmentFormDto) {
        iSysTemplateLibraryService.save(sysDepartmentFormDto);
        return ResultDataUtil.ok("添加模板库信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = "删除模板库信息")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除模板库信息", notes = "删除模板库信息", position = 2)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Integer id) {
        iSysTemplateLibraryService.removeTree(id);
        return ResultDataUtil.ok("删除模板库信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "修改模板库信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改模板库信息", notes = "修改模板库信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysTemplateLibraryFormDto sysTemplateLibraryFormDto) {
        iSysTemplateLibraryService.updateById(sysTemplateLibraryFormDto);
        return ResultDataUtil.ok("修改模板库信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", description = "查询模板库单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询模板库单条信息", notes = " 查询模板库单条信息", position = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysTemplateLibrary> findById(@PathVariable Integer id) {
        SysTemplateLibrary sysTemplateLibrary = iSysTemplateLibraryService.getById(id);
        return ResultDataUtil.ok("查询模板库单条信息成功", sysTemplateLibrary);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", description = "查询档案库列表信息树")
    @GetMapping
    @ApiOperation(value = "查询档案库列表信息树", notes = "查询档案库列表信息树", position = 5)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysTemplateLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询档案库列表信息树成功", treeNodeUtils);
    }

}
