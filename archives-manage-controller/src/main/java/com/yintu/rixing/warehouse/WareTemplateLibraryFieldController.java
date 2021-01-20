package com.yintu.rixing.warehouse;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysTemplateLibraryFieldTypeService;
import com.yintu.rixing.system.SysTemplateLibraryFieldType;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 库房管理实体库字段表 前端控制器
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/warehouse/ware-template-library-field")
public class WareTemplateLibraryFieldController {
    @Autowired
    private IWareTemplateLibraryFieldService iWareTemplateLibraryFieldService;
    @Autowired
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;

    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "查询模板库字段类型列表信息")
    @GetMapping("/findTemplateLibraryFieldType")
    @ApiOperation(value = "查询模板库字段类型列表信息", notes = "查询模板库字段类型列表信息")
    public ResultDataUtil<List<SysTemplateLibraryFieldType>> findSysTemplateLibraryFieldTypes() {
        List<SysTemplateLibraryFieldType> sysTemplateLibraryFieldTypes = iSysTemplateLibraryFieldTypeService.list(new QueryWrapper<SysTemplateLibraryFieldType>().orderByDesc("id"));
        return ResultDataUtil.ok("查询模板库字段类型列表信息成功", sysTemplateLibraryFieldTypes);
    }

    @PostMapping("/add")
    @Log(level = EnumLogLevel.DEBUG, module = "库房管理", context = "添加新的库房管理实体表字段信息")
    @ApiOperation(value = "添加新的库房管理实体表字段信息", notes = "添加新的库房管理实体表字段信息")
    public Map<String,Object> add(WareTemplateLibraryField wareTemplateLibraryField){
        iWareTemplateLibraryFieldService.save(wareTemplateLibraryField);
        return ResponseDataUtil.ok("新增添加新的库房管理实体表字段信息成功");
    }

    @DeleteMapping("/{ids}")
    @Log(level = EnumLogLevel.WARN, module = "库房管理", context = "根据id删除库房管理实体表字段信息")
    @ApiOperation(value = "根据id删除库房管理实体表字段信息", notes = "根据id删除库房管理实体表字段信息")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true ,paramType="path")
    public Map<String, Object> remove(@PathVariable Set<Integer> ids) {
        if (iWareTemplateLibraryFieldService.removeByIds(ids)){
            return ResponseDataUtil.ok("删除库房管理实体表字段信息成功");
        }
        return ResponseDataUtil.error("删除库房管理实体表字段信息失败");
    }

    @PutMapping("/{id}")
    @Log(level = EnumLogLevel.INFO,module = "库房管理",context = "根据id编辑对应的库房管理实体表字段信息")
    @ApiOperation(value = "根据id编辑对应的库房管理实体表字段信息",notes = "根据id编辑对应的库房管理实体表字段信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String,Object>edit(@PathVariable Integer id,WareTemplateLibraryField wareTemplateLibraryField){
        iWareTemplateLibraryFieldService.updateById(wareTemplateLibraryField);
        return ResponseDataUtil.ok("修改库房管理实体表字段信息成功");
    }

    @GetMapping
    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "查询库房管理实体表字段列表信息")
    @ApiOperation(value = "库房管理实体表字段列表信息", notes = " 库房管理实体表字段列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10")
    })
    public Map<String,Object>findWareTemplateLibraryFieldDatas(@RequestParam Integer num, @RequestParam Integer size){
        QueryWrapper<WareTemplateLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("`order`");
        Page<WareTemplateLibraryField> wareTemplateLibraryFieldPage=iWareTemplateLibraryFieldService.page(new Page<>(num,size),queryWrapper);
        for (WareTemplateLibraryField record : wareTemplateLibraryFieldPage.getRecords()) {
           record.setSysTemplateLibraryFieldType(iSysTemplateLibraryFieldTypeService.getById( record.getTemplateLibraryFieldTypeId()));
        }
        return ResponseDataUtil.ok("查询库房管理实体表字段列表信息成功",wareTemplateLibraryFieldPage);
    }

    @Log(level = EnumLogLevel.INFO, module = "库房管理", context = "修改库房管理实体表字段顺序")
    @PatchMapping("/{id1}/{id2}")
    @ApiOperation(value = "修改库房管理实体表字段顺序", notes = "修改库房管理实体表字段顺序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id1", dataType = "int", value = "主键id1", required = true, paramType = "path"),
            @ApiImplicitParam(name = "id2", dataType = "int", value = "主键id2", required = true, paramType = "path")
    })
    public ResultDataUtil<Object> editOrder(@PathVariable Integer id1, @PathVariable Integer id2) {
        iWareTemplateLibraryFieldService.updateOrderByIds(id1, id2);
        return ResultDataUtil.ok("修改库房管理实体表字段顺序成功");
    }

}
