package com.yintu.rixing.data;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.data.DataCommonMarkDto;
import com.yintu.rixing.dto.data.DataCommonRollBackDto;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.enumobject.EnumAuthType;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.ISysDepartmentService;
import com.yintu.rixing.system.ISysUserService;
import com.yintu.rixing.system.SysDepartment;
import com.yintu.rixing.util.ObjectConvertUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@ApiSupport(order = 3)
public class Data03FormalLibraryController extends Authenticator {

    @Autowired
    private IDataFormalLibraryService iDataFormalLibraryService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询正式库列表信息")
    @GetMapping
    @ApiOperation(value = "查询正式库列表信息", notes = "查询正式库列表信息")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<DataCommonVo> findPage(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataFormalLibraryService.getPage(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询正式库列表信息成功", dataCommonVo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询正式库档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询正式库档案库列表信息树", notes = "查询正式库档案库列表信息树")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils;
        if (EnumAuthType.ADMIN.getValue().equals(this.getUserAuthType())) {
            treeNodeUtils = iSysArchivesLibraryService.listTree(TreeUtil.ROOT_PARENT_ID);
        } else {
            treeNodeUtils = iSysUserService.listSysArchivesLibraryTree(this.getLoginUserId(), TreeUtil.ROOT_PARENT_ID);
        }
        return ResultDataUtil.ok("查询正式库档案库列表信息树成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询正式库组织机构机构列表信息")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询正式库组织机构列表信息", notes = "查询正式库组织机构列表信息")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<List<SysDepartment>> findList() {
        List<SysDepartment> sysDepartments = iSysDepartmentService.list(new QueryWrapper<SysDepartment>().orderByDesc("id"));
        return ResultDataUtil.ok("查询正式库组织机构列表信息成功", sysDepartments);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "批量导出正式库信息")
    @GetMapping("/export/{ids}")
    @ApiOperation(value = "批量导出正式库信息", notes = "批量导出正式库信息")
    @ApiOperationSupport(order = 4)
    public void exportExcelDataFile(HttpServletResponse response, @PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) throws IOException {
        iDataFormalLibraryService.exportExcelRecordFile(response, EnumArchivesOrder.FORMAL_LIBRARY.getName(), ids, archivesLibraryId);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "普通搜索正式库列表信息")
    @GetMapping("/findPage")
    @ApiOperation(value = "普通搜索正式库列表信息", notes = "普通搜索正式库列表信息")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<DataCommonVo> findPageEasy(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataFormalLibraryService.getPageEasy(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询正式库列表信息成功", dataCommonVo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "高级搜索正式库列表信息")
    @PostMapping("/findPage")
    @ApiOperation(value = "高级搜索正式库列表信息", notes = "高级搜索正式库列表信息")
    @ApiOperationSupport(order = 6)
    public ResultDataUtil<DataCommonVo> findPageComplex(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataFormalLibraryService.getPageComplex(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询正式库列表信息成功", dataCommonVo);
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "正式库信息标记/取消为病档")
    @PatchMapping("/mark")
    @ApiOperation(value = "正式库信息标记/取消为病档信息", notes = "正式库信息标记/取消为病档信息")
    @ApiOperationSupport(order = 7)
    public ResultDataUtil<Object> mark(@Validated DataCommonMarkDto dataCommonMarkDto) {
        iDataFormalLibraryService.mark(dataCommonMarkDto);
        if(dataCommonMarkDto.getType() == 1){
            return ResultDataUtil.ok("正式库信息标记为病档信息成功");
        }else{
            return ResultDataUtil.ok("正式库信息取消病档信息成功");
        }
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "正式库信息回退整理库")
    @PatchMapping("/rollback")
    @ApiOperation(value = "正式库信息回退整理库", notes = "正式库信息回退整理库")
    @ApiOperationSupport(order = 8)
    public ResultDataUtil<Object> rollback(@Validated DataCommonRollBackDto dataCommonRollBackDto) {
        iDataFormalLibraryService.rollBack(dataCommonRollBackDto);
        return ResultDataUtil.ok("正式库信息回退整理库成功");
    }

}
