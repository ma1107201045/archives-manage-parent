package com.yintu.rixing.data;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.util.ObjectConvertUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 数据整理库 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:16:08
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-Sorting-library")
@Api(tags = "整理库接口")
@ApiSort(2)
public class Data02SortingLibraryController extends Authenticator {

    @Autowired
    private IDataSortingLibraryService dataSortingLibraryService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

    @Log(level = EnumLogLevel.DEBUG, module = "数据中心", context = "添加整理库信息")
    @PostMapping
    @ApiOperation(value = "添加整理库信息", notes = "添加整理库信息")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<Object> add(@RequestParam Map<String, String> params) {
        dataSortingLibraryService.save(ObjectConvertUtil.getAddFormDto(params));
        return ResultDataUtil.ok("添加整理库信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "删除整理库信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除整理库信息", notes = "删除整理库信息")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) {
        dataSortingLibraryService.removeByIds(ids, archivesLibraryId);
        return ResultDataUtil.ok("删除整理库信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "修改整理库信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改整理库信息", notes = "修改整理库信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @RequestParam Map<String, String> params) {
        params.put(ObjectConvertUtil.ID, id.toString());
        dataSortingLibraryService.updateById(ObjectConvertUtil.getNotAddFormDto(params));
        return ResultDataUtil.ok("修改整理库信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "回退临时库")
    @PatchMapping("/rollback/{id}")
    @ApiOperation(value = "回退临时库", notes = "回退临时库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 4)
    public ResultDataUtil<Object> rollback(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        dataSortingLibraryService.updateStatusById(id, archivesLibraryId, (short) 1);
        return ResultDataUtil.ok("回退临时库成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "移交正式库")
    @PatchMapping("/turn-over/{id}")
    @ApiOperation(value = "移交正式库", notes = "移交正式库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<Object> turnOver(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        dataSortingLibraryService.updateStatusById(id, archivesLibraryId, (short) 3);
        return ResultDataUtil.ok("移交正式库成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询整理库单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询整理库单条信息", notes = "查询整理库单条信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 6)
    public ResultDataUtil<Object> findById(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        dataSortingLibraryService.getById(id, archivesLibraryId);
        return ResultDataUtil.ok("查询整理库单条信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询整理库列表信息")
    @GetMapping
    @ApiOperation(value = "查询整理库列表信息", notes = "查询整理库列表信息")
    @ApiOperationSupport(order = 7)
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<DataCommonVo> findPage(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = dataSortingLibraryService.getPage(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询整理库列表信息成功", dataCommonVo);
    }


    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询整理库档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询整理库档案库列表信息树", notes = "查询整理库档案库列表信息树")
    @ApiOperationSupport(order = 8)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询整理库档案库列表信息树成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "下载档案收集信息模板")
    @GetMapping("/download-template")
    @ApiOperation(value = "下载整理库信息模板", notes = "下载整理库信息模板")
    @ApiOperationSupport(order = 10)
    public void exportExcelTemplateFile(HttpServletResponse response, @RequestParam Integer archivesLibraryId) throws IOException {
        dataSortingLibraryService.exportExcelTemplateFile(response, EnumArchivesOrder.ARCHIVES_COLLECTION.getName(), archivesLibraryId);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导出整理库信息")
    @GetMapping("/export/{ids}")
    @ApiOperation(value = "批量导出整理库信息", notes = "批量导出整理库信息")
    @ApiOperationSupport(order = 11)
    public void exportExcelDataFile(HttpServletResponse response, @PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) throws IOException {
        dataSortingLibraryService.exportExcelRecordFile(response, EnumArchivesOrder.ARCHIVES_COLLECTION.getName(), ids, archivesLibraryId);
    }

}
