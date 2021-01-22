package com.yintu.rixing.data;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
@ApiSort(0)
public class Data00ArchivesCollectionController extends Authenticator {

    @Autowired
    private IDataTemporaryLibraryService iDataTemporaryLibraryService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导入档案收集信息")
    @PostMapping("/import")
    @ApiOperation(value = "批量导入档案收集信息", notes = "批量导入档案收集信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件对象", required = true, dataType = "__file", paramType = "form"),
            @ApiImplicitParam(name = "archivesLibraryId", value = "档案库id", required = true, dataType = "int", paramType = "form")
    })
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<Object> importExcelData(@RequestParam("file") MultipartFile multipartFile, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataTemporaryLibraryService.importExcelRecord(multipartFile, archivesLibraryId);
        return ResultDataUtil.ok("批量导入档案收集信息成功");
    }


    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案收集档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询档案收集档案库列表信息树", notes = "查询档案收集档案库列表信息树")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询档案收集档案库列表信息树成功", treeNodeUtils);
    }

}
