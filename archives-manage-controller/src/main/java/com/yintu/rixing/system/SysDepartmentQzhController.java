package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysDepartmentQzhFromDto;
import com.yintu.rixing.dto.system.SysDepartmentQzhQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequestMapping("/system/sys-department/sys-qzh")
@ApiSort(9)
@Api(tags = "全宗号接口")
public class SysDepartmentQzhController extends Authenticator implements BaseController<SysDepartmentQzhFromDto, SysDepartmentQzhQueryDto, SysDepartmentQzh, Integer> {

    @Autowired
    private ISysDepartmentIdQzhService iSysDepartmentIdQzhService;

    @Autowired
    private ISysDepartmentService iSysDepartmentService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", context = "添加全宗号信息")
    @PostMapping
    @ApiOperation(value = "添加全宗号信息", notes = "添加全宗号信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysDepartmentQzhFromDto formDto) {
        iSysDepartmentIdQzhService.save(formDto);
        return ResultDataUtil.ok("添加全宗号信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", context = "删除全宗号信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除全宗号信息", notes = "删除全宗号信息", position = 2)
    @ApiImplicitParam(name = "ids", value = "主键id集合", required = true)
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysDepartmentIdQzhService.removeByIds(ids);
        return ResultDataUtil.ok("删除全宗号信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = " 修改全宗号信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改全宗号信息", notes = "修改全宗号信息", position = 3)
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysDepartmentQzhFromDto formDto) {
        iSysDepartmentIdQzhService.updateById(formDto);
        return ResultDataUtil.ok("修改全宗号信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询全宗号信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询全宗号信息", notes = " 查询全宗号单条信息", position = 4)
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public ResultDataUtil<SysDepartmentQzh> findById(@PathVariable Integer id) {
        SysDepartmentQzh sysQzh = iSysDepartmentIdQzhService.getById(id);
        return ResultDataUtil.ok("查询全宗号信息成功", sysQzh);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询全宗号信息列表")
    @GetMapping
    @ApiOperation(value = "查询全宗号信息列表", notes = " 查询全宗号信息列表", position = 5)
    public ResultDataUtil<Page<SysDepartmentQzh>> findPage(SysDepartmentQzhQueryDto queryDto) {
        Page<SysDepartmentQzh> page = iSysDepartmentIdQzhService.page(queryDto);
        return ResultDataUtil.ok("查询全宗号信息列表成功", page);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统管理", context = "查询全宗号部门列表信息树")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询全宗号部门列表信息树", notes = "查询全宗号部门列表信息树", position = 6)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysDepartmentService.listTree(-1);
        return ResultDataUtil.ok("查询全宗号部门列表信息树成功", treeNodeUtils);
    }
}
