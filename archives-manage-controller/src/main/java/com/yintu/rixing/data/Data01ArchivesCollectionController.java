package com.yintu.rixing.data;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ObjectConvertUtil;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 数据档案收集 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:08:46
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-archives-collection")
@Api(tags = "档案收集接口")
@ApiSort(1)
public class Data01ArchivesCollectionController extends Authenticator {
    @Autowired
    private IDataArchivesCollectionService iDataArchivesCollectionService;

    @Log(level = EnumLogLevel.DEBUG, module = "数据中心", context = "添加档案收集信息")
    @PostMapping
    @ApiOperation(value = "添加档案收集信息", notes = "添加档案收集信息", position = 1)
    public ResultDataUtil<Object> add(@RequestParam Map<String, String> params) {
        iDataArchivesCollectionService.save(ObjectConvertUtil.getAddDto(params));
        return ResultDataUtil.ok("添加档案收集信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "删除档案收集信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除档案收集信息", notes = "删除档案收集信息", position = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids, @RequestParam Integer archivesId) {
        iDataArchivesCollectionService.removeByIds(ids, archivesId);
        return ResultDataUtil.ok("删除档案收集信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "修改档案收集信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改档案收集信息", notes = "修改档案收集信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @RequestParam Map<String, String> params) {
        params.put(ObjectConvertUtil.ID, id.toString());
        iDataArchivesCollectionService.updateById(ObjectConvertUtil.getEditDto(params));
        return ResultDataUtil.ok("修改档案收集信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案收集单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询档案收集单条信息", notes = "查询档案收集单条信息", position = 4)
    public ResultDataUtil<Object> findById(@RequestParam Integer archivesId, @PathVariable Integer id) {
        iDataArchivesCollectionService.getById(archivesId, id);
        return ResultDataUtil.ok("查询档案收集单条信息成功");
    }

}
