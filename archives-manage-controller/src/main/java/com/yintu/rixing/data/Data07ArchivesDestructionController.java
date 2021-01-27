package com.yintu.rixing.data;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.ISysDepartmentService;
import com.yintu.rixing.system.SysDepartment;
import com.yintu.rixing.util.ObjectConvertUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 数据档案销毁 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:24:45
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-archives-destruction")
@Api(tags = "档案销毁接口")
@ApiSort(8)
public class Data07ArchivesDestructionController extends Authenticator {

    @Autowired
    private IDataArchivesDestructionService iDataArchivesDestructionService;

    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "删除档案销毁信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除档案销毁信息", notes = "删除档案销毁信息")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) {
        iDataArchivesDestructionService.removeByIds(ids, archivesLibraryId);
        return ResultDataUtil.ok("删除档案销毁信息成功");
    }


    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案销毁单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询档案销毁单条信息", notes = "查询档案销毁单条信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<Object> findById(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataArchivesDestructionService.getById(id, archivesLibraryId);
        return ResultDataUtil.ok("查询档案销毁单条信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案销毁列表信息")
    @GetMapping
    @ApiOperation(value = "查询档案销毁列表信息", notes = "查询档案销毁列表信息")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<DataCommonVo> findPage(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataArchivesDestructionService.getPage(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询档案销毁列表信息成功", dataCommonVo);
    }


    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案销毁档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询档案销毁档案库列表信息树", notes = "查询档案销毁档案库列表信息树")
    @ApiOperationSupport(order = 4)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询档案销毁档案库列表信息树成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询销毁库组织机构机构列表信息")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询档案销毁组织机构列表信息", notes = "查询档案销毁组织机构列表信息")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<List<SysDepartment>> findList() {
        List<SysDepartment> sysDepartments = iSysDepartmentService.list(new QueryWrapper<SysDepartment>().orderByDesc("id"));
        return ResultDataUtil.ok("查询档案销毁组织机构列表信息成功", sysDepartments);
    }
}
