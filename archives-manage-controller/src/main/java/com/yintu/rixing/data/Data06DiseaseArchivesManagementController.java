package com.yintu.rixing.data;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 数据病档管理 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:22:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/disease-archives-management")
@Api(tags = "病档管理接口")
@ApiSupport(order = 7)
public class Data06DiseaseArchivesManagementController extends Authenticator {
    @Autowired
    private IDataDiseaseArchivesManagementService iDataDiseaseArchivesManagementService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "删除病档管理信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除病档管理信息", notes = "删除病档管理信息")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) {
        iDataDiseaseArchivesManagementService.removeByIds(ids, archivesLibraryId);
        return ResultDataUtil.ok("删除病档管理信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "取消病档管理信息")
    @PatchMapping("/{id}")
    @ApiOperation(value = "取消病档管理信息", notes = "取消病档管理信息")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    public ResultDataUtil<Object> cancel(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataDiseaseArchivesManagementService.mark(id, archivesLibraryId);
        return ResultDataUtil.ok("取消病档管理信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询病档管理单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询病档管理单条信息", notes = "查询病档管理单条信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<Object> findById(@PathVariable Integer id, @RequestParam Integer archivesLibraryId) {
        iDataDiseaseArchivesManagementService.getById(id, archivesLibraryId);
        return ResultDataUtil.ok("查询病档管理单条信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询病档管理列表信息")
    @GetMapping
    @ApiOperation(value = "查询病档管理列表信息", notes = "查询病档管理列表信息")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParam(name = "params", dataType = "map", value = "参数集", required = true, paramType = "query")
    public ResultDataUtil<DataCommonVo> findPage(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataDiseaseArchivesManagementService.getPage(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询病档管理列表信息成功", dataCommonVo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询病档管理档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询病档管理档案库列表信息树", notes = "查询病档管理档案库列表信息树")
    @ApiOperationSupport(order = 5)
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils;
        if (EnumAuthType.ADMIN.getValue().equals(this.getUserAuthType())) {
            treeNodeUtils = iSysArchivesLibraryService.listTree(TreeUtil.ROOT_PARENT_ID);
        } else {
            treeNodeUtils = iSysUserService.listSysArchivesLibraryTree(this.getLoginUserId(), TreeUtil.ROOT_PARENT_ID);
        }
        return ResultDataUtil.ok("查询病档管理档案库列表信息树成功", treeNodeUtils);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询销毁库组织机构机构列表信息")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询销毁库组织机构列表信息", notes = "查询销毁库组织机构列表信息")
    @ApiOperationSupport(order = 6)
    public ResultDataUtil<List<SysDepartment>> findList() {
        List<SysDepartment> sysDepartments = iSysDepartmentService.list(new QueryWrapper<SysDepartment>().orderByDesc("id"));
        return ResultDataUtil.ok("查询销毁库组织机构列表信息成功", sysDepartments);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "普通搜索病档管理列表信息")
    @GetMapping("/findPage")
    @ApiOperation(value = "普通搜索病档管理列表信息", notes = "普通搜索病档管理列表信息")
    @ApiOperationSupport(order = 7)
    public ResultDataUtil<DataCommonVo> findPageEasy(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataDiseaseArchivesManagementService.getPageEasy(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询病档管理列表信息成功", dataCommonVo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "高级搜索病档管理列表信息")
    @PostMapping("/findPage")
    @ApiOperation(value = "高级搜索病档管理列表信息", notes = "高级搜索病档管理列表信息")
    @ApiOperationSupport(order = 8)
    public ResultDataUtil<DataCommonVo> findPageComplex(@RequestParam Map<String, String> params) {
        DataCommonVo dataCommonVo = iDataDiseaseArchivesManagementService.getPageComplex(ObjectConvertUtil.getQueryDto(params));
        return ResultDataUtil.ok("查询病档管理列表信息成功", dataCommonVo);
    }
}
