package com.yintu.rixing.data;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysArchivesLibraryService;
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
 * 数据回收站 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:18:27
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-recycle-bin")
@Api(tags = "回收站接口")
@ApiSort(4)
public class Data04RecycleBinController extends Authenticator {

    @Autowired
    private IDataRecycleBinService iDataRecycleBinService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "彻底删除回收站信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "彻底删除回收站信息", notes = "彻底删除回收站信息")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) {
        iDataRecycleBinService.removeByIds(ids, archivesLibraryId);
        return ResultDataUtil.ok("彻底删除回收站信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "恢复回收站信息")
    @PatchMapping("/{ids}")
    @ApiOperation(value = "恢复回收站信息", notes = "恢复回收站信息")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    public ResultDataUtil<Object> regain(@PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) {
        for (Integer id : ids) {
            iDataRecycleBinService.updateStatusById(id, archivesLibraryId);
        }
        return ResultDataUtil.ok("恢复回收站信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询回收站单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询回收站单条信息", notes = "查询回收站单条信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<Object> findById(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataRecycleBinService.getById(id, archivesLibraryId);
        return ResultDataUtil.ok("查询整理库单条信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询正式库列表信息")
    @GetMapping
    @ApiOperation(value = "查询正式库列表信息", notes = "查询正式库列表信息")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<DataCommonVo> findPage(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataRecycleBinService.getPage(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询正式库列表信息成功", dataCommonVo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询正式库档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询正式库档案库列表信息树", notes = "查询正式库档案库列表信息树")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询正式库档案库列表信息树成功", treeNodeUtils);
    }

}
