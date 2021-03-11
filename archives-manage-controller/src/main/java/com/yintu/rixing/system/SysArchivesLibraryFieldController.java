package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysArchivesLibraryFieldFormDto;
import com.yintu.rixing.dto.system.SysArchivesLibraryFieldQueryDto;
import com.yintu.rixing.dto.system.SysArchivesLibraryNumberSettingDto;
import com.yintu.rixing.dto.system.SysCommonFieldLibraryQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "档案库字段接口")
@ApiSupport(order = 10)
public class SysArchivesLibraryFieldController extends Authenticator implements BaseController<SysArchivesLibraryFieldFormDto, SysArchivesLibraryFieldQueryDto, SysArchivesLibraryField, Integer> {

    @Autowired
    private ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;
    @Autowired
    private ISysArchivesLibraryNumberSettingService iSysArchivesLibraryNumberSettingService;
    @Autowired
    private ISysDataTypeService iSysDataTypeService;
    @Autowired
    private ISysCommonFieldLibraryService iSysCommonFieldLibraryService;


    @Override
    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "添加档案库字段信息")
    @PostMapping
    @ApiOperation(value = "添加档案库字段信息", notes = "添加档案库字段信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> add(@Validated SysArchivesLibraryFieldFormDto formDto) {
        iSysArchivesLibraryFieldService.save(formDto);
        return ResultDataUtil.ok("添加档案库字段信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.WARN, module = "系统设置", context = "删除档案库字段信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除档案库字段信息", notes = "删除档案库字段信息")
    @ApiImplicitParam(name = "ids", dataType = "int", value = "主键id集", required = true, paramType = "path")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysArchivesLibraryFieldService.removeByIds(ids);
        return ResultDataUtil.ok("删除档案库字段信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = "修改档案库字段信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改档案库字段信息", notes = "修改档案库字段信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysArchivesLibraryFieldFormDto formDto) {
        iSysArchivesLibraryFieldService.updateById(formDto);
        return ResultDataUtil.ok("修改档案库字段信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = "修改档案库字段顺序")
    @PatchMapping("/{id1}/{id2}")
    @ApiOperation(value = "修改档案库字段顺序", notes = "修改档案库字段顺序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id1", dataType = "int", value = "主键id1", required = true, paramType = "path"),
            @ApiImplicitParam(name = "id2", dataType = "int", value = "主键id2", required = true, paramType = "path")
    })
    @ApiOperationSupport(order = 4)
    public ResultDataUtil<Object> editOrder(@PathVariable Integer id1, @PathVariable Integer id2) {
        iSysArchivesLibraryFieldService.updateOrderByIds(id1, id2);
        return ResultDataUtil.ok("修改档案库字段顺序成功");
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询档案库字段单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询档案库字段单条信息", notes = "查询档案库字段单条信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<SysArchivesLibraryField> findById(@PathVariable Integer id) {
        SysArchivesLibraryField sysArchivesLibraryField = iSysArchivesLibraryFieldService.getById(id);
        return ResultDataUtil.ok("查询档案库字段单条信息成功", sysArchivesLibraryField);
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询档案库字段列表信息")
    @GetMapping
    @ApiOperation(value = "查询档案库字段列表信息", notes = "查询档案库字段列表信息")
    @ApiOperationSupport(order = 6)
    public ResultDataUtil<Page<SysArchivesLibraryField>> findPage(@Validated SysArchivesLibraryFieldQueryDto queryDto) {
        Page<SysArchivesLibraryField> page = iSysArchivesLibraryFieldService.page(queryDto);
        return ResultDataUtil.ok("查询档案库字段列表信息成功", page);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询档案库字段数据类型列表信息")
    @GetMapping("/sys-data-type")
    @ApiOperation(value = "查询档案库字段数据类型列表信息", notes = "查询档案库字段数据类型列表信息")
    @ApiOperationSupport(order = 7)
    public ResultDataUtil<List<SysDataType>> findSysDataTypes() {
        List<SysDataType> sysTemplateLibraryFieldTypes = iSysDataTypeService.list(new QueryWrapper<SysDataType>().orderByDesc("id"));
        return ResultDataUtil.ok("查询档案库字段数据类型列表信息成功", sysTemplateLibraryFieldTypes);
    }


    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "选择公共字段库信息")
    @PostMapping("/sys-common-field-library")
    @ApiOperation(value = "档案设置", notes = "选择公共字段库信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "commonFieldLibraries", allowMultiple = true, dataType = "__int", value = "公共字段库id集", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 8)
    public ResultDataUtil<Object> chooseSysCommonFieldLibrary(@RequestParam Integer archivesLibraryId, @RequestParam List<Integer> commonFieldLibraries) {
        iSysArchivesLibraryFieldService.chooseSysCommonFieldLibrary(archivesLibraryId, commonFieldLibraries);
        return ResultDataUtil.ok("选择公共字段库信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询档案库字段公共字段库信息列表")
    @GetMapping("/sys-common-field-library")
    @ApiOperation(value = "查询档案库字段公共字段库信息列表", notes = "查询档案库字段公共字段库信息列表")
    @ApiOperationSupport(order = 9)
    public ResultDataUtil<Page<SysCommonFieldLibrary>> findSysCommonFieldLibrary(@Validated SysCommonFieldLibraryQueryDto sysCommonFieldLibraryQueryDto) {
        Page<SysCommonFieldLibrary> sysCommonFieldLibraries = iSysCommonFieldLibraryService.page(sysCommonFieldLibraryQueryDto);
        return ResultDataUtil.ok("查询档案库字段公共字段库信息列表成功", sysCommonFieldLibraries);
    }


    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "档案设置")
    @PostMapping("/sys-archives-library-number-setting")
    @ApiOperation(value = "档案设置", notes = "档案设置")
    @ApiOperationSupport(order = 10)
    public ResultDataUtil<Object> archivesLibraryNumberSetting(@RequestBody List<SysArchivesLibraryNumberSettingDto> sysArchivesLibraryNumberSettingDtos) {
        iSysArchivesLibraryNumberSettingService.archivesLibraryNumberSetting(sysArchivesLibraryNumberSettingDtos);
        return ResultDataUtil.ok("档案设置成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询档案设置信息")
    @GetMapping("/sys-archives-library-number-setting")
    @ApiOperation(value = "档案设置", notes = "档案设置")
    @ApiOperationSupport(order = 11)
    @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    public ResultDataUtil<List<SysArchivesLibraryNumberSettingDto>> archivesLibraryNumberSetting(@RequestParam Integer archivesLibraryId) {
        List<SysArchivesLibraryNumberSettingDto> sysArchivesLibraryNumberSettingVos = iSysArchivesLibraryNumberSettingService.findByArchivesLibraryId(archivesLibraryId);
        return ResultDataUtil.ok("查询档案设置信息成功", sysArchivesLibraryNumberSettingVos);
    }
}
