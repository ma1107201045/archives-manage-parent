package com.yintu.rixing.data;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.enumobject.EnumAuthType;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.ObjectConvertUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@ApiSupport(order = 0)
public class Data00ArchivesCollectionController extends Authenticator {

    @Autowired
    private IDataTemporaryLibraryService iDataTemporaryLibraryService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;


    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询临时库列表信息")
    @GetMapping
    @ApiOperation(value = "查询档案收集列表信息", notes = "查询档案收集列表信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<DataCommonVo> findPage(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataTemporaryLibraryService.getPage(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询档案收集列表信息成功", dataCommonVo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案收集档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询档案收集档案库列表信息树", notes = "查询档案收集档案库列表信息树")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils;
        if (EnumAuthType.ADMIN.getValue().equals(this.getUserAuthType())) {
            treeNodeUtils = iSysArchivesLibraryService.listTree(TreeUtil.ROOT_PARENT_ID);
        } else {
            treeNodeUtils = iSysUserService.listSysArchivesLibraryTree(this.getLoginUserId(), TreeUtil.ROOT_PARENT_ID);
        }
        return ResultDataUtil.ok("查询档案收集档案库列表信息树成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案收集组织机构列表信息")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询档案收集组织机构列表信息", notes = "查询档案收集组织机构列表信息")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<List<SysDepartment>> findList() {
        List<SysDepartment> sysDepartments = iSysDepartmentService.list(new QueryWrapper<SysDepartment>().orderByDesc("id"));
        return ResultDataUtil.ok("查询档案收集组织机构列表信息成功", sysDepartments);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "下载档案收集信息模板")
    @GetMapping("/download-template")
    @ApiOperation(value = "下载档案收集信息模板", notes = "下载档案收集信息模板")
    @ApiOperationSupport(order = 4)
    public void exportExcelTemplateFile(HttpServletResponse response, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataTemporaryLibraryService.exportExcelTemplateFile(response, EnumArchivesOrder.ARCHIVES_COLLECTION.getName(), archivesLibraryId);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导入档案收集信息")
    @PostMapping("/import")
    @ApiOperation(value = "批量导入档案收集信息", notes = "批量导入档案收集信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件对象", required = true, dataType = "__file", paramType = "form"),
            @ApiImplicitParam(name = "archivesLibraryId", value = "档案库id", required = true, dataType = "int", paramType = "form")
    })
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<Object> importExcelData(@RequestParam("file") MultipartFile multipartFile, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataTemporaryLibraryService.importExcelRecord(multipartFile, archivesLibraryId);
        return ResultDataUtil.ok("批量导入档案收集信息成功");
    }

}
