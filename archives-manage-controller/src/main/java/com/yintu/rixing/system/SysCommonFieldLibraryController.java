package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysCommonFieldLibraryFormDto;
import com.yintu.rixing.dto.system.SysCommonFieldLibraryQueryDto;
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
 * 系统公共字段库表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@RestController
@RequestMapping("/system/sys-common-field-library")
@Api(tags = "公共字段库接口")
@ApiSupport(order = 8)
public class SysCommonFieldLibraryController extends Authenticator implements BaseController<SysCommonFieldLibraryFormDto, SysCommonFieldLibraryQueryDto, SysCommonFieldLibrary, Integer> {

    @Autowired
    private ISysCommonFieldLibraryService iSysCommonFieldLibraryService;
    @Autowired
    private ISysDataTypeService iSysDataTypeService;

    @Override
    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "添加公共字段库信息")
    @PostMapping
    @ApiOperation(value = "添加公共字段库信息", notes = "添加公共字段库信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysCommonFieldLibraryFormDto dto) {
        iSysCommonFieldLibraryService.save(dto);
        return ResultDataUtil.ok("添加公共字段库信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.WARN, module = "系统设置", context = "删除公共字段库信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除公共字段库信息", notes = "删除公共字段库信息", position = 2)
    @ApiImplicitParam(name = "ids", dataType = "int", value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysCommonFieldLibraryService.removeByIds(ids);
        return ResultDataUtil.ok("删除公共字段库信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = "修改公共字段库信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改公共字段库信息", notes = "修改公共字段库信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysCommonFieldLibraryFormDto dto) {
        iSysCommonFieldLibraryService.updateById(dto);
        return ResultDataUtil.ok("修改公共字段库信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = "修改公共字段库顺序")
    @PatchMapping("/{id1}/{id2}")
    @ApiOperation(value = "修改公共字段库顺序", notes = "修改公共字段库顺序", position = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id1", dataType = "int", value = "主键id1", required = true, paramType = "path"),
            @ApiImplicitParam(name = "id2", dataType = "int", value = "主键id2", required = true, paramType = "path")
    })
    public ResultDataUtil<Object> editOrder(@PathVariable Integer id1, @PathVariable Integer id2) {
        iSysCommonFieldLibraryService.updateOrderByIds(id1, id2);
        return ResultDataUtil.ok("修改公共字段库顺序成功");
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询公共字段库单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询公共字段库单条信息", notes = "查询公共字段库单条信息", position = 5)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysCommonFieldLibrary> findById(@PathVariable Integer id) {
        SysCommonFieldLibrary sysTemplateLibraryField = iSysCommonFieldLibraryService.getById(id);
        return ResultDataUtil.ok("查询公共字段库单条信息成功", sysTemplateLibraryField);
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询公共字段库列表信息")
    @GetMapping
    @ApiOperation(value = "查询公共字段库列表信息", notes = "查询公共字段库列表信息", position = 6)
    public ResultDataUtil<Page<SysCommonFieldLibrary>> findPage(@Validated SysCommonFieldLibraryQueryDto queryDto) {
        Page<SysCommonFieldLibrary> page = iSysCommonFieldLibraryService.page(queryDto);
        return ResultDataUtil.ok("查询公共字段库列表信息成功", page);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询公共字段库类型列表信息")
    @GetMapping("/sys-data-type")
    @ApiOperation(value = "查询公共字段库类型列表信息", notes = "查询公共字段库类型列表信息", position = 7)
    public ResultDataUtil<List<SysDataType>> findSysTemplateLibraryFieldTypes() {
        List<SysDataType> sysTemplateLibraryFieldTypes = iSysDataTypeService.list(new QueryWrapper<SysDataType>().orderByDesc("id"));
        return ResultDataUtil.ok("查询公共字段库类型列表信息成功", sysTemplateLibraryFieldTypes);
    }
}
