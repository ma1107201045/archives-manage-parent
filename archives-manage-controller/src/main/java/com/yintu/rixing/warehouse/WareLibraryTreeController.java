package com.yintu.rixing.warehouse;


import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 库房管理的树表 前端控制器
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/warehouse/ware-library-tree")
public class WareLibraryTreeController {
    @Autowired
    private IWareLibraryTreeService iWareLibraryTreeService;

    //添加根目录
    @PostMapping("/addParent")
    @Log(level = EnumLogLevel.DEBUG, module = "库房管理", context = "新增库房管理根目录")
    @ApiOperation(value = "新增库房管理根目录", notes = "新增库房管理根目录")
    public ResultDataUtil<Object> addParent(WareLibraryTree wareLibraryTree) {
        wareLibraryTree.setParentId(-1);
        wareLibraryTree.setType(1);
        iWareLibraryTreeService.save(wareLibraryTree);
        return ResultDataUtil.ok("新增库房管理根目录成功");
    }

    //添加根目录
    @PostMapping("/add")
    @Log(level = EnumLogLevel.DEBUG, module = "库房管理", context = "新增库房管理子节点目录")
    @ApiOperation(value = "新增库房管理子节点目录", notes = "新增库房管理子节点目录")
    public ResultDataUtil<Object> add(WareLibraryTree wareLibraryTree) {
        wareLibraryTree.setType(1);
        iWareLibraryTreeService.save(wareLibraryTree);
        return ResultDataUtil.ok("新增库房管理子节点目录成功");
    }

    //根据id编辑 编辑对应的目录数据
    @PutMapping("/{id}")
    @Log(level = EnumLogLevel.INFO, module = "库房管理", context = "根据id编辑库房管理目录数据")
    @ApiOperation(value = "根据id编辑库房管理目录数据", notes = "根据id编辑库房管理目录数据")
    public ResultDataUtil<Object> update(@PathVariable Integer id, WareLibraryTree wareLibraryTree) {
        iWareLibraryTreeService.updateById(wareLibraryTree);
        return ResultDataUtil.ok("编辑库房管理目录数据成功");
    }

    //根据id 删除对应的目录数据
    @DeleteMapping("/{id}")
    @Log(level = EnumLogLevel.WARN, module = "库房管理", context = "根据id删除库房管理目录数据")
    @ApiOperation(value = "根据id删除库房管理目录数据", notes = "根据id删除库房管理目录数据")
    public ResultDataUtil<Object>delete(@PathVariable Integer id){
        iWareLibraryTreeService.removeById(id);
        return ResultDataUtil.ok("删除库房管理目录数据成功");
    }

    //查询树结构
    @GetMapping("/findTree")
    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "查询库房管理目录数据树结构")
    @ApiOperation(value = "查询库房管理目录数据树结构", notes = "查询库房管理目录数据树结构")
    public ResultDataUtil<List<TreeUtil>>findTree(){
        List<TreeUtil> treeNodeUtils = iWareLibraryTreeService.listTree(-1);
        return ResultDataUtil.ok("查询库房管理目录数据树结构成功",treeNodeUtils);
    }

}
