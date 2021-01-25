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
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<Object> add(@RequestParam Map<String, String> params) {
        iDataTemporaryLibraryService.save(ObjectConvertUtil.getAddFormDto(params));
        return ResultDataUtil.ok("添加临时库信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "删除临时库信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除临时库信息", notes = "删除临时库信息")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) {
        iDataTemporaryLibraryService.removeByIds(ids, archivesLibraryId);
        return ResultDataUtil.ok("删除临时库信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "修改临时库信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改临时库信息", notes = "修改临时库信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @RequestParam Map<String, String> params) {
        params.put(ObjectConvertUtil.ID, id.toString());
        iDataTemporaryLibraryService.updateById(ObjectConvertUtil.getNotAddFormDto(params));
        return ResultDataUtil.ok("修改临时库信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "临时库信息移交到整理库")
    @PatchMapping("/turn-over/{id}")
    @ApiOperation(value = "临时库信息移交到整理库", notes = "临时库信息移交到整理库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 4)
    public ResultDataUtil<Object> turnOver(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataTemporaryLibraryService.updateStatusById(id, archivesLibraryId, (short) 2);
        return ResultDataUtil.ok("临时库信息移交到整理库成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "临时库信息标记为病档信息")
    @PatchMapping("/mark/{id}")
    @ApiOperation(value = "临时库信息标记为病档信息", notes = "临时库信息标记为病档信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<Object> mark(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataTemporaryLibraryService.updateStatusById(id, archivesLibraryId);
        return ResultDataUtil.ok("临时库信息标记为病档信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询临时库单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询临时库单条信息", notes = "查询临时库单条信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 6)
    public ResultDataUtil<Object> findById(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataTemporaryLibraryService.getById(id, archivesLibraryId);
        return ResultDataUtil.ok("查询临时库单条信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询临时库列表信息")
    @GetMapping
    @ApiOperation(value = "查询临时库列表信息", notes = "查询临时库列表信息")
    @ApiOperationSupport(order = 7)
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<DataCommonVo> findPage(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataTemporaryLibraryService.getPage(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询临时库列表信息成功", dataCommonVo);
    }


    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询临时库档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询临时库档案库列表信息树", notes = "查询临时库档案库列表信息树")
    @ApiOperationSupport(order = 8)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询临时库档案库列表信息树成功", treeNodeUtils);
    }


    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导出临时库信息")
    @GetMapping("/export/{ids}")
    @ApiOperation(value = "批量导出临时库信息", notes = "批量导出临时库信息")
    @ApiOperationSupport(order = 9)
    public void exportExcelDataFile(HttpServletResponse response, @PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataTemporaryLibraryService.exportExcelRecordFile(response, EnumArchivesOrder.TEMPORARY_LIBRARY.getName(), ids, archivesLibraryId);
    }
}
