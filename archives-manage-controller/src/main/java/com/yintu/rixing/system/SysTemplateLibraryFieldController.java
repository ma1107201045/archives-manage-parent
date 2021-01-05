package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
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
 * 系统模板库字段表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@RestController
@RequestMapping("/system/sys-template-library-field")
@ApiSort(7)
@Api(tags = "模块库字段接口")
public class SysTemplateLibraryFieldController extends AuthenticationController implements BaseController<SysTemplateLibraryFieldFormDto, SysTemplateLibraryFieldQueryDto, SysTemplateLibraryField, Integer> {

    @Autowired
    private ISysTemplateLibraryFieldService iSysTemplateLibraryFieldService;
    @Autowired
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "添加模板库字段信息")
    @PostMapping
    @ApiOperation(value = "添加模板库字段信息", notes = "添加模板库字段信息", position = 1)
    public ResultDataUtil<Object> add(SysTemplateLibraryFieldFormDto dto) {
        iSysTemplateLibraryFieldService.save(dto);
        return ResultDataUtil.ok("添加模板库字段信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = "删除模板库字段信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除模板库字段信息", notes = "删除模板库字段信息", position = 2)
    @ApiImplicitParam(name = "ids", dataType = "int", value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysTemplateLibraryFieldService.removeByIds(ids);
        return ResultDataUtil.ok("删除模板库字段信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "修改模板库字段信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改模板库字段信息", notes = "修改模板库字段信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, SysTemplateLibraryFieldFormDto dto) {
        iSysTemplateLibraryFieldService.updateById(dto);
        return ResultDataUtil.ok("修改模板库字段信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", description = "查询模板库字段单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询模板库字段单条信息", notes = "查询模板库字段单条信息", position = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysTemplateLibraryField> findById(@PathVariable Integer id) {
        SysTemplateLibraryField sysTemplateLibraryField = iSysTemplateLibraryFieldService.getById(id);
        return ResultDataUtil.ok("查询模板库字段单条信息成功", sysTemplateLibraryField);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", description = "查询模板库字段列表信息")
    @GetMapping
    @ApiOperation(value = "查询模板库字段列表信息", notes = "查询模板库字段列表信息", position = 5)
    public ResultDataUtil<Page<SysTemplateLibraryField>> findPage(@Validated SysTemplateLibraryFieldQueryDto queryDto) {
        Page<SysTemplateLibraryField> page = iSysTemplateLibraryFieldService.page(queryDto);
        return ResultDataUtil.ok("查询模板库字段列表信息成功", page);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", description = "查询模板库字段类型列表信息")
    @GetMapping("/sys-template-library-field-type")
    @ApiOperation(value = "查询模板库字段类型列表信息", notes = "查询模板库字段类型列表信息", position = 6)
    public ResultDataUtil<List<SysTemplateLibraryFieldType>> findSysTemplateLibraryFieldTypes() {
        List<SysTemplateLibraryFieldType> sysTemplateLibraryFieldTypes = iSysTemplateLibraryFieldTypeService.list(new QueryWrapper<SysTemplateLibraryFieldType>().orderByDesc("id"));
        return ResultDataUtil.ok("查询模板库字段类型列表信息成功", sysTemplateLibraryFieldTypes);
    }
}