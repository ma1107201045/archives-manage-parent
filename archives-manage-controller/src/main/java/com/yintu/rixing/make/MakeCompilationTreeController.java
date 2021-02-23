package com.yintu.rixing.make;


import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.warehouse.WareLibraryTree;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 档案编研的树表 前端控制器
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/MakeCompilationTree/make-compilation-tree")
public class MakeCompilationTreeController {
    @Autowired
    private IMakeCompilationTreeService iMakeCompilationTreeService;

    //添加根目录
    @PostMapping("/addParent")
    @Log(level = EnumLogLevel.DEBUG, module = "利用中心", context = "新增档案编研根目录")
    @ApiOperation(value = "新增档案编研根目录", notes = "新增档案编研根目录")
    public ResultDataUtil<Object> addParent(MakeCompilationTree makeCompilationTree) {
        makeCompilationTree.setParentId(-1);
        makeCompilationTree.setType(1);
        iMakeCompilationTreeService.save(makeCompilationTree);
        return ResultDataUtil.ok("新增档案编研根目录成功");
    }

    //添加根目录
    @PostMapping("/add")
    @Log(level = EnumLogLevel.DEBUG, module = "利用中心", context = "新增档案编研子节点目录")
    @ApiOperation(value = "新增档案编研子节点目录", notes = "新增档案编研子节点目录")
    public ResultDataUtil<Object> add(MakeCompilationTree makeCompilationTree) {
        makeCompilationTree.setType(1);
        iMakeCompilationTreeService.save(makeCompilationTree);
        return ResultDataUtil.ok("新增档案编研子节点目录成功");
    }

    //根据id编辑 编辑对应的目录数据
    @PutMapping("/{id}")
    @Log(level = EnumLogLevel.INFO, module = "利用中心", context = "根据id编辑档案编研目录数据")
    @ApiOperation(value = "根据id编辑档案编研目录数据", notes = "根据id编辑档案编研目录数据")
    public ResultDataUtil<Object> update(@PathVariable Integer id, MakeCompilationTree makeCompilationTree) {
        iMakeCompilationTreeService.updateById(makeCompilationTree);
        return ResultDataUtil.ok("编辑档案编研目录数据成功");
    }

    //根据id 删除对应的目录数据
    @DeleteMapping("/{id}")
    @Log(level = EnumLogLevel.WARN, module = "利用中心", context = "根据id删除档案编研目录数据")
    @ApiOperation(value = "根据id删除档案编研目录数据", notes = "根据id删除档案编研目录数据")
    public ResultDataUtil<Object>delete(@PathVariable Integer id){
        iMakeCompilationTreeService.removeById(id);
        return ResultDataUtil.ok("删除档案编研目录数据成功");
    }

    //查询树结构
    @GetMapping("/findTree")
    @Log(level = EnumLogLevel.TRACE, module = "利用中心", context = "查询档案编研目录数据树结构")
    @ApiOperation(value = "查询档案编研目录数据树结构", notes = "查询档案编研目录数据树结构")
    public ResultDataUtil<List<TreeUtil>>findTree(){
        List<TreeUtil> treeNodeUtils = iMakeCompilationTreeService.listTree(-1);
        return ResultDataUtil.ok("查询档案编研目录数据树结构成功",treeNodeUtils);
    }


}
