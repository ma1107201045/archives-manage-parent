package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysArchivesLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysArchivesLibraryFieldQueryDto;
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
 * 系统档案库字段表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2021-01-14
 */
@RestController
@RequestMapping("/system/sys-archives-library-field")
@ApiSort(9)
@Api(tags = "档案库字段接口")
public class SysArchivesLibraryFieldController extends Authenticator implements BaseController<SysArchivesLibraryFieldFormDto, SysArchivesLibraryFieldQueryDto, SysArchivesLibraryField, Integer> {

    @Autowired
    private ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;
    @Autowired
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "添加档案库字段信息")
    @PostMapping
    @ApiOperation(value = "添加档案库字段信息", notes = "添加档案库字段信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysArchivesLibraryFieldFormDto formDto) {
        iSysArchivesLibraryFieldService.save(formDto);
        return ResultDataUtil.ok("添加档案库字段信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统设置", context = "删除档案库字段信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除档案库字段信息", notes = "删除档案库字段信息", position = 2)
    @ApiImplicitParam(name = "ids", dataType = "int", value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysArchivesLibraryFieldService.removeByIds(ids);
        return ResultDataUtil.ok("删除档案库字段信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = "修改档案库字段信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改档案库字段信息", notes = "修改档案库字段信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysArchivesLibraryFieldFormDto formDto) {
        iSysArchivesLibraryFieldService.updateById(formDto);
        return ResultDataUtil.ok("修改档案库字段信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = "修改档案库字段顺序")
    @PatchMapping("/{id1}/{id2}")
    @ApiOperation(value = "修改档案库字段顺序", notes = "修改档案库字段顺序", position = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id1", dataType = "int", value = "主键id1", required = true, paramType = "path"),
            @ApiImplicitParam(name = "id2", dataType = "int", value = "主键id2", required = true, paramType = "path")
    })
    public ResultDataUtil<Object> editOrder(@PathVariable Integer id1, @PathVariable Integer id2) {
        iSysArchivesLibraryFieldService.updateOrderByIds(id1, id2);
        return ResultDataUtil.ok("修改档案库字段顺序成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询档案库字段单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询档案库字段单条信息", notes = "查询档案库字段单条信息", position = 5)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysArchivesLibraryField> findById(@PathVariable Integer id) {
        SysArchivesLibraryField sysArchivesLibraryField = iSysArchivesLibraryFieldService.getById(id);
        return ResultDataUtil.ok("查询档案库字段单条信息成功", sysArchivesLibraryField);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询档案库字段列表信息")
    @GetMapping
    @ApiOperation(value = "查询档案库字段列表信息", notes = "查询档案库字段列表信息", position = 6)
    public ResultDataUtil<Page<SysArchivesLibraryField>> findPage(@Validated SysArchivesLibraryFieldQueryDto queryDto) {
        Page<SysArchivesLibraryField> page = iSysArchivesLibraryFieldService.page(queryDto);
        return ResultDataUtil.ok("查询档案库字段列表信息成功", page);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询档案库字段类型列表信息")
    @GetMapping("/sys-template-library-field-type")
    @ApiOperation(value = "查询档案库字段类型列表信息", notes = "查询档案库字段类型列表信息", position = 7)
    public ResultDataUtil<List<SysTemplateLibraryFieldType>> findSysTemplateLibraryFieldTypes() {
        List<SysTemplateLibraryFieldType> sysTemplateLibraryFieldTypes = iSysTemplateLibraryFieldTypeService.list(new QueryWrapper<SysTemplateLibraryFieldType>().orderByDesc("id"));
        return ResultDataUtil.ok("查询档案库字段类型列表信息成功", sysTemplateLibraryFieldTypes);
    }
}
