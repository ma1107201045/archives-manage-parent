package com.yintu.rixing.data;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
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
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 数据临时库 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:12:39
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-temporary-library")
@Api(tags = "临时库接口")
@ApiSort(1)
public class Data01TemporaryLibraryController extends Authenticator {

    @Autowired
    private IDataTemporaryLibraryService iDataTemporaryLibraryService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

    @Log(level = EnumLogLevel.DEBUG, module = "数据中心", context = "添加临时库信息")
    @PostMapping
    @ApiOperation(value = "添加临时库信息", notes = "添加临时库信息")
    @ApiOperationSupport(order = 1)
    @ApiIgnore
    public ResultDataUtil<Object> add(@RequestParam Map<String, String> params) {
        iDataTemporaryLibraryService.save(ObjectConvertUtil.getAddFormDto(params));
        return ResultDataUtil.ok("添加临时库信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "删除临时库信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除临时库信息", notes = "删除临时库信息")
    @ApiImplicitParam(name = "ids", allowMultiple = true, value = "主键id集", required = true, paramType = "path")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) {
        iDataTemporaryLibraryService.removeByIds(ids, archivesLibraryId);
        return ResultDataUtil.ok("删除临时库信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "修改临时库信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改临时库信息", notes = "修改临时库信息")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @RequestParam Map<String, String> params) {
        params.put(ObjectConvertUtil.ID, id.toString());
        iDataTemporaryLibraryService.updateById(ObjectConvertUtil.getNotAddFormDto(params));
        return ResultDataUtil.ok("修改临时库信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询临时库单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询临时库单条信息", notes = "查询临时库单条信息")
    @ApiOperationSupport(order = 4)
    public ResultDataUtil<Object> findById(@PathVariable Integer id, @RequestParam Map<String, String> params) {
        params.put(ObjectConvertUtil.ID, id.toString());
        iDataTemporaryLibraryService.getById(ObjectConvertUtil.getNotAddFormDto(params));
        return ResultDataUtil.ok("查询临时库单条信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询临时库列表信息")
    @GetMapping
    @ApiOperation(value = "查询临时库列表信息", notes = "查询临时库列表信息")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<DataCommonVo> findPage(@Validated DataCommonQueryDto dataCommonQueryDto) {
        DataCommonVo dataCommonVo = iDataTemporaryLibraryService.getPage(dataCommonQueryDto);
        return ResultDataUtil.ok("查询临时库列表信息成功", dataCommonVo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询临时库档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询临时库档案库列表信息树", notes = "查询临时库档案库列表信息树")
    @ApiOperationSupport(order = 6)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询临时库档案库列表信息树成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导入临时库信息")
    @PostMapping("/import")
    @ApiOperation(value = "批量导入临时库信息", notes = "批量导入临时库信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件对象", required = true, dataType = "__file", paramType = "form"),
            @ApiImplicitParam(name = "archivesLibraryId", value = "档案库id", required = true, dataType = "int", paramType = "form")
    })
    @ApiOperationSupport(order = 7)
    public ResultDataUtil<Object> importExcelData(@RequestParam("file") MultipartFile multipartFile, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataTemporaryLibraryService.importExcelRecord(multipartFile, archivesLibraryId);
        return ResultDataUtil.ok("批量导入临时库信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "下载临时库信息模板")
    @GetMapping("/download-template")
    @ApiOperation(value = "下载临时库信息模板", notes = "下载临时库信息模板")
    @ApiOperationSupport(order = 8)
    public void exportExcelTemplateFile(HttpServletResponse response, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataTemporaryLibraryService.exportExcelTemplateFile(response, EnumArchivesOrder.ARCHIVES_COLLECTION.getName(), archivesLibraryId);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导出临时库信息")
    @GetMapping("/export/{ids}")
    @ApiOperation(value = "批量导出临时库信息", notes = "批量导出临时库信息")
    @ApiOperationSupport(order = 9)
    public void exportExcelDataFile(HttpServletResponse response, @PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataTemporaryLibraryService.exportExcelRecordFile(response, EnumArchivesOrder.ARCHIVES_COLLECTION.getName(), ids, archivesLibraryId);
    }
}
