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
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 数据正式库 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:14:15
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-formal-library")
@Api(tags = "正式库接口")
@ApiSort(3)
public class Data03FormalLibraryController extends Authenticator {

    @Autowired
    private IDataFormalLibraryService iDataFormalLibraryService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;


    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询正式库列表信息")
    @GetMapping
    @ApiOperation(value = "查询正式库列表信息", notes = "查询正式库列表信息")
    @ApiOperationSupport(order = 7)
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<DataCommonVo> findPage(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataFormalLibraryService.getPage(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询正式库列表信息成功", dataCommonVo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询正式库档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询正式库档案库列表信息树", notes = "查询正式库档案库列表信息树")
    @ApiOperationSupport(order = 8)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询正式库档案库列表信息树成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导出正式库信息")
    @GetMapping("/export/{ids}")
    @ApiOperation(value = "批量导出正式库信息", notes = "批量导出正式库信息")
    @ApiOperationSupport(order = 11)
    public void exportExcelDataFile(HttpServletResponse response, @PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataFormalLibraryService.exportExcelRecordFile(response, EnumArchivesOrder.ARCHIVES_COLLECTION.getName(), ids, archivesLibraryId);
    }


}
