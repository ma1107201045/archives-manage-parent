package com.yintu.rixing.data;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.util.ObjectConvertUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
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
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

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
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) {
        iDataArchivesCollectionService.removeByIds(ids, archivesLibraryId);
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
    @ApiOperation(value = "查询档案收集列表信息", notes = "查询档案收集列表信息")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<DataCommonVo> findPage(@Validated DataCommonQueryDto dataCommonQueryDto) {
        DataCommonVo dataCommonVo = iDataArchivesCollectionService.getPage(dataCommonQueryDto);
        return ResultDataUtil.ok("查询档案收集列表信息成功", dataCommonVo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案收集档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询档案收集档案库列表信息树", notes = "查询档案收集档案库列表信息树")
    @ApiOperationSupport(order = 6)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询档案收集档案库列表信息树成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导入档案收集信息")
    @PostMapping("/import}")
    @ApiOperation(value = "批量导入档案收集信息", notes = "批量导入档案收集信息")
    @ApiOperationSupport(order = 7)
    public ResultDataUtil<Object> importExcelData(HttpServletRequest request, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataArchivesCollectionService.importExcelRecord(request, archivesLibraryId);
        return ResultDataUtil.ok("批量导入档案收集信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "下载档案收集信息模板")
    @GetMapping("/download-template")
    @ApiOperation(value = "下载档案收集信息模板", notes = "下载档案收集信息模板")
    @ApiOperationSupport(order = 8)
    public void exportExcelTemplateFile(HttpServletResponse response, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataArchivesCollectionService.exportExcelTemplateFile(response, "档案收集-模板", archivesLibraryId);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导出档案收集信息")
    @GetMapping("/export/{ids}")
    @ApiOperation(value = "批量导出档案收集信息", notes = "批量导出档案收集信息")
    @ApiOperationSupport(order = 9)
    public void exportExcelDataFile(HttpServletResponse response, @PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataArchivesCollectionService.exportExcelRecordFile(response, "档案收集-记录", ids, archivesLibraryId);
    }

}
