package com.yintu.rixing.data;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ObjectConvertUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import io.swagger.annotations.*;
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
    @ApiOperation(value = "添加档案收集信息", notes = "添加档案收集信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> add(@RequestParam Map<String, String> params) {
        iDataArchivesCollectionService.save(ObjectConvertUtil.getAddFormDto(params));
        return ResultDataUtil.ok("添加档案收集信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "删除档案收集信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除档案收集信息", notes = "删除档案收集信息")
    @ApiImplicitParam(name = "ids", allowMultiple = true, value = "主键id集", required = true, paramType = "path")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids, @RequestParam Integer archivesId) {
        iDataArchivesCollectionService.removeByIds(ids, archivesId);
        return ResultDataUtil.ok("删除档案收集信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "修改档案收集信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改档案收集信息", notes = "修改档案收集信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @RequestParam Map<String, String> params) {
        params.put(ObjectConvertUtil.ID, id.toString());
        iDataArchivesCollectionService.updateById(ObjectConvertUtil.getNotAddFormDto(params));
        return ResultDataUtil.ok("修改档案收集信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案收集单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询档案收集单条信息", notes = "查询档案收集单条信息")
    @ApiOperationSupport(order = 4)
    public ResultDataUtil<Object> findById(@PathVariable Integer id, @RequestParam Map<String, String> params) {
        params.put(ObjectConvertUtil.ID, id.toString());
        iDataArchivesCollectionService.getById(ObjectConvertUtil.getNotAddFormDto(params));
        return ResultDataUtil.ok("查询档案收集单条信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案收集列表信息")
    @GetMapping
    @ApiOperation(value = "查询档案收集列表信息", notes = "查询档案收集列表信息", position = 6)
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<DataCommonVo> findPage(DataCommonQueryDto dataCommonQueryDto) {
        DataCommonVo dataCommonVo = iDataArchivesCollectionService.getPage(dataCommonQueryDto);
        return ResultDataUtil.ok("查询档案收集列表信息成功", dataCommonVo);
    }

}
