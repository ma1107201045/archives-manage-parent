package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysQzhFromDto;
import com.yintu.rixing.dto.system.SysQzhQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-11-27
 */
@RestController
@RequestMapping("/system/sys-qzh")
@ApiSort(9)
@Api(tags = "全宗号接口")
public class SysQzhController extends Authenticator implements BaseController<SysQzhFromDto, SysQzhQueryDto, SysQzh, Integer> {

    @Autowired
    private ISysQzhService iSysQzhService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加全宗号信息")
    @PostMapping
    @ApiOperation(value = "添加全宗号信息", notes = "添加全宗号信息")
    public ResultDataUtil<Object> add(@Validated SysQzhFromDto formDto) {
        iSysQzhService.save(formDto);
        return ResultDataUtil.ok("添加全宗号信息成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除全宗号信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除全宗号信息", notes = "删除全宗号信息")
    @ApiImplicitParam(name = "ids", value = "主键id", required = true)
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysQzhService.removeByIds(ids);
        return ResultDataUtil.ok("删除全宗号信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = " 修改全宗号信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改全宗号信息", notes = "修改全宗号信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysQzhFromDto formDto) {
        iSysQzhService.updateById(formDto);
        return ResultDataUtil.ok("修改全宗号信息成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询全宗号信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询全宗号信息", notes = " 查询全宗号单条信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public ResultDataUtil<SysQzh> findById(@PathVariable Integer id) {
        SysQzh sysQzh = iSysQzhService.getById(id);
        return ResultDataUtil.ok("查询全宗号信息成功", sysQzh);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询全宗号信息列表")
    @GetMapping
    @ApiOperation(value = "查询全宗号信息列表", notes = " 查询全宗号信息列表")
    public ResultDataUtil<Page<SysQzh>> findPage(SysQzhQueryDto queryDto) {
        Page<SysQzh> page = iSysQzhService.page(queryDto);
        return ResultDataUtil.ok("查询全宗号信息列表成功", page);
    }
}
