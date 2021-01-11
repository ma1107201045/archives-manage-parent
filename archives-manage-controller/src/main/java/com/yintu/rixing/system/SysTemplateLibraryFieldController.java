package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysTemplateLibraryFieldQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.*;
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
@ApiSort(6)
@Api(tags = "模块库字段接口")
public class SysTemplateLibraryFieldController extends Authenticator implements BaseController<SysTemplateLibraryFieldFormDto, SysTemplateLibraryFieldQueryDto, SysTemplateLibraryField, Integer> {

    @Autowired
    private ISysTemplateLibraryFieldService iSysTemplateLibraryFieldService;
    @Autowired
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", context = "添加模板库字段信息")
    @PostMapping
    @ApiOperation(value = "添加模板库字段信息", notes = "添加模板库字段信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysTemplateLibraryFieldFormDto dto) {
        iSysTemplateLibraryFieldService.save(dto);
        return ResultDataUtil.ok("添加模板库字段信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", context = "删除模板库字段信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除模板库字段信息", notes = "删除模板库字段信息", position = 2)
    @ApiImplicitParam(name = "ids", dataType = "int", value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysTemplateLibraryFieldService.removeByIds(ids);
        return ResultDataUtil.ok("删除模板库字段信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = "修改模板库字段信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改模板库字段信息", notes = "修改模板库字段信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id,@Validated SysTemplateLibraryFieldFormDto dto) {
        iSysTemplateLibraryFieldService.updateById(dto);
        return ResultDataUtil.ok("修改模板库字段信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = "修改模板库字段顺序")
    @PatchMapping("/{id1}/{id2}")
    @ApiOperation(value = "修改模板库字段顺序", notes = "修改模板库字段顺序", position = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id1", dataType = "int", value = "主键id1", required = true, paramType = "path"),
            @ApiImplicitParam(name = "id2", dataType = "int", value = "主键id2", required = true, paramType = "path")
    })
    public ResultDataUtil<Object> editOrder(@PathVariable Integer id1, @PathVariable Integer id2) {
        iSysTemplateLibraryFieldService.updateOrderByIds(id1, id2);
        return ResultDataUtil.ok("修改模板库字段顺序成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询模板库字段单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询模板库字段单条信息", notes = "查询模板库字段单条信息", position = 5)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysTemplateLibraryField> findById(@PathVariable Integer id) {
        SysTemplateLibraryField sysTemplateLibraryField = iSysTemplateLibraryFieldService.getById(id);
        return ResultDataUtil.ok("查询模板库字段单条信息成功", sysTemplateLibraryField);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询模板库字段列表信息")
    @GetMapping
    @ApiOperation(value = "查询模板库字段列表信息", notes = "查询模板库字段列表信息", position = 6)
    public ResultDataUtil<Page<SysTemplateLibraryField>> findPage(@Validated SysTemplateLibraryFieldQueryDto queryDto) {
        Page<SysTemplateLibraryField> page = iSysTemplateLibraryFieldService.page(queryDto);
        return ResultDataUtil.ok("查询模板库字段列表信息成功", page);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询模板库字段类型列表信息")
    @GetMapping("/sys-template-library-field-type")
    @ApiOperation(value = "查询模板库字段类型列表信息", notes = "查询模板库字段类型列表信息", position = 7)
    public ResultDataUtil<List<SysTemplateLibraryFieldType>> findSysTemplateLibraryFieldTypes() {
        List<SysTemplateLibraryFieldType> sysTemplateLibraryFieldTypes = iSysTemplateLibraryFieldTypeService.list(new QueryWrapper<SysTemplateLibraryFieldType>().orderByDesc("id"));
        return ResultDataUtil.ok("查询模板库字段类型列表信息成功", sysTemplateLibraryFieldTypes);
    }
}
