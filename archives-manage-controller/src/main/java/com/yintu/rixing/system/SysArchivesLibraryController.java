package com.yintu.rixing.system;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.system.SysArchivesLibraryFormDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统档案库表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2021-01-11
 */
@RestController
@RequestMapping("/system/sys-archives-library")
@Api(tags = "档案库接口")
@ApiSupport(order = 9)
public class SysArchivesLibraryController extends Authenticator {

    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;


    @Log(level = EnumLogLevel.DEBUG, module = "系统设置", context = "添加档案库信息")
    @PostMapping
    @ApiOperation(value = "添加档案库信息", notes = "添加档案库信息", position = 1)
    public ResultDataUtil<Object> add(@Validated SysArchivesLibraryFormDto formDto) {
        iSysArchivesLibraryService.save(formDto);
        return ResultDataUtil.ok("添加档案库信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统设置", context = "删除档案库信息")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除档案库信息", notes = "删除档案库信息", position = 2)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Integer id) {
        iSysArchivesLibraryService.removeTree(id);
        return ResultDataUtil.ok("删除档案库信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统设置", context = "修改档案库信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改档案库信息", notes = "修改档案库信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysArchivesLibraryFormDto formDto) {
        iSysArchivesLibraryService.updateById(formDto);
        return ResultDataUtil.ok("修改档案库信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询档案库单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询档案库单条信息", notes = " 查询档案库单条信息", position = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<SysArchivesLibrary> findById(@PathVariable Integer id) {
        SysArchivesLibrary sysTemplateLibrary = iSysArchivesLibraryService.getById(id);
        return ResultDataUtil.ok("查询档案库单条信息成功", sysTemplateLibrary);
    }

    @Log(level = EnumLogLevel.TRACE, module = "系统设置", context = "查询档案库列表信息树")
    @GetMapping
    @ApiOperation(value = "查询档案库列表信息树", notes = "查询档案库列表信息树", position = 5)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(TreeUtil.ROOT_PARENT_ID);
        return ResultDataUtil.ok("查询档案库列表信息树成功", treeNodeUtils);
    }

}
