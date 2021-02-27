package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.*;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 系统数据字典表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2021-02-27
 */

@RestController
@RequestMapping("/system/sys-dictionaries")
@Api(tags = "数据字典接口接口")
@ApiSupport(order = 12)
public class SysDictionariesController extends Authenticator implements BaseController<SysDictionariesFormDto, SysDictionariesQueryDto, SysDictionaries, Integer> {
    @Autowired
    private ISysDictionariesService iSysDictionariesService;
    @Autowired
    private ISysDictionariesDetailsService iSysDictionariesDetailsService;

    @Override
    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "添加数据字典信息")
    @PostMapping
    @ApiOperation(value = "添加数据字典信息", notes = "添加数据字典信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> add(@Validated SysDictionariesFormDto formDto) {
        iSysDictionariesService.save(formDto);
        return ResultDataUtil.ok("添加数据字典信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.WARN, module = "系统设置", context = "删除数据字典信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysDictionariesService.removeByIds(ids);
        return ResultDataUtil.ok("删除数据字典信息成功");
    }

    @Override
    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = " 修改数据字典信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改数据字典信息", notes = "修改数据字典信息")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysDictionariesFormDto formDto) {
        iSysDictionariesService.updateById(formDto);
        return ResultDataUtil.ok("修改数据字典信成功");
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询数据字典单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询数据字典单条信息", notes = " 查询数据字典单条信息")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysDictionaries> findById(@PathVariable Integer id) {
        SysDictionaries sysDictionaries = iSysDictionariesService.getById(id);
        return ResultDataUtil.ok("查询数据字典单条信息成功", sysDictionaries);
    }

    @Override
    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询数据字典列表信息")
    @GetMapping
    @ApiOperation(value = "查询数据字典列表信息", notes = "查询数据字典列表信息")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<Page<SysDictionaries>> findPage(SysDictionariesQueryDto queryDto) {
        Page<SysDictionaries> page = iSysDictionariesService.page(queryDto);
        return ResultDataUtil.ok("查询数据字典列表信息成功", page);
    }


    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询数据字典详情列表信息")
    @GetMapping("/{dictionariesId}/sys-dictionaries-details")
    @ApiOperation(value = "查询数据字典详情列表信息", notes = "查询数据字典详情列表信息")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<Page<SysDictionariesDetails>> findSysDictionariesDetails(@PathVariable Integer dictionariesId, SysDictionariesDetailsQueryDto sysDictionariesDetailsQueryDto) {
        Page<SysDictionariesDetails> page = iSysDictionariesDetailsService.page(sysDictionariesDetailsQueryDto);
        return ResultDataUtil.ok("查询数据字典详情列表信息成功", page);
    }
}
