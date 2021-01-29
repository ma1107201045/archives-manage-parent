package com.yintu.rixing.data;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
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
@RequestMapping("/data/data-sorting-library")
@Api(tags = "整理库接口")
@ApiSort(2)
public class Data02SortingLibraryController extends Authenticator {

    @Autowired
    private IDataSortingLibraryService iDataSortingLibraryService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;

    @Log(level = EnumLogLevel.DEBUG, module = "数据中心", context = "添加整理库信息")
    @PostMapping
    @ApiOperation(value = "添加整理库信息", notes = "添加整理库信息")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<Object> add(@RequestParam Map<String, String> params) {
        iDataSortingLibraryService.save(ObjectConvertUtil.getAddFormDto(params));
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
        iDataSortingLibraryService.removeByIds(ids, archivesLibraryId);
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
        iDataSortingLibraryService.updateById(ObjectConvertUtil.getNotAddFormDto(params));
        return ResultDataUtil.ok("修改整理库信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "整理库信息回退整理库")
    @PatchMapping("/rollback/{id}")
    @ApiOperation(value = "整理库信息回退整理库", notes = "整理库信息回退整理库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 4)
    public ResultDataUtil<Object> rollback(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataSortingLibraryService.updateStatusById(id, archivesLibraryId, EnumArchivesOrder.TEMPORARY_LIBRARY.getValue());
        return ResultDataUtil.ok("整理库信息回退整理库成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "整理库信息移交正式库")
    @PatchMapping("/turn-over/{id}")
    @ApiOperation(value = "整理库信息移交正式库", notes = "整理库信息移交正式库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<Object> turnOver(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataSortingLibraryService.updateStatusById(id, archivesLibraryId, EnumArchivesOrder.FORMAL_LIBRARY.getValue());
        return ResultDataUtil.ok("整理库信息移交正式库成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "整理库信息标记为病档")
    @PatchMapping("/mark/{id}")
    @ApiOperation(value = "整理库信息标记为病档信息", notes = "整理库信息标记为病档信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 6)
    public ResultDataUtil<Object> mark(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataSortingLibraryService.updateStatusById(id, archivesLibraryId, EnumArchivesOrder.DISEASE_ARCHIVES.getValue());
        return ResultDataUtil.ok("整理库信息标记为病档信息成功");
    }


    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询整理库单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询整理库单条信息", notes = "查询整理库单条信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 7)
    public ResultDataUtil<Object> findById(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataSortingLibraryService.getById(id, archivesLibraryId);
        return ResultDataUtil.ok("查询整理库单条信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询整理库列表信息")
    @GetMapping
    @ApiOperation(value = "查询整理库列表信息", notes = "查询整理库列表信息")
    @ApiOperationSupport(order = 8)
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<DataCommonVo> findPage(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataSortingLibraryService.getPage(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询整理库列表信息成功", dataCommonVo);
    }


    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询整理库档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询整理库档案库列表信息树", notes = "查询整理库档案库列表信息树")
    @ApiOperationSupport(order = 9)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询整理库档案库列表信息树成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询整理库部门机构列表信息")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询整理库部门列表信息", notes = "查询整理库部门列表信息")
    @ApiOperationSupport(order = 10)
    public ResultDataUtil<List<SysDepartment>> findList() {
        List<SysDepartment> sysDepartments = iSysDepartmentService.list(new QueryWrapper<SysDepartment>().orderByDesc("id"));
        return ResultDataUtil.ok("查询整理库部门列表信息成功", sysDepartments);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导入整理库信息")
    @PostMapping("/import")
    @ApiOperation(value = "批量导入整理库信息", notes = "批量导入整理库信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", dataType = "__file", value = "文件对象", required = true, paramType = "form"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "form")
    })
    @ApiOperationSupport(order = 11)
    public ResultDataUtil<Object> importExcelData(@RequestParam("file") MultipartFile multipartFile, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataSortingLibraryService.importExcelRecord(multipartFile, archivesLibraryId);
        return ResultDataUtil.ok("批量导入整理库信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "下载整理库信息模板")
    @GetMapping("/download-template")
    @ApiOperation(value = "下载整理库信息模板", notes = "下载整理库信息模板")
    @ApiOperationSupport(order = 12)
    public void exportExcelTemplateFile(HttpServletResponse response, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataSortingLibraryService.exportExcelTemplateFile(response, EnumArchivesOrder.SORTING_LIBRARY.getName(), archivesLibraryId);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导出整理库信息")
    @GetMapping("/export/{ids}")
    @ApiOperation(value = "批量导出整理库信息", notes = "批量导出整理库信息")
    @ApiOperationSupport(order = 13)
    public void exportExcelDataFile(HttpServletResponse response, @PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataSortingLibraryService.exportExcelRecordFile(response, EnumArchivesOrder.SORTING_LIBRARY.getName(), ids, archivesLibraryId);
    }

}
