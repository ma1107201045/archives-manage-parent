package com.yintu.rixing.make;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.make.MakeArchivesSearchDto;
import com.yintu.rixing.enumobject.EnumAuthType;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.pojo.MakeArchivesSearchPojo;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import com.yintu.rixing.vo.make.MakeArchivesSearchElectronicVo;
import com.yintu.rixing.vo.make.MakeArchivesSearchVo;
import com.yintu.rixing.warehouse.WareTemplateLibraryField;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:54:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("/make/make-archives-search")
@Api("档案检索接口")
@ApiSupport(order = 4)
public class MakeArchivesSearchController extends Authenticator {
    @Autowired
    private IMakeArchivesSearchService iMakeArchivesSearchService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private ISysBaseFieldLibraryService iSysArchivesLibraryFieldDefaultService;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;

    //根据条件查询实体库数据
    @Log(level = EnumLogLevel.DEBUG, module = "档案检索", context = "根据条件查询实体档案数据信息列表")
    @GetMapping("/searchEntityArchives")
    @ApiOperation(value = "根据条件查询实体档案数据信息列表", notes = " 根据条件查询实体档案数据信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "searchThings", value = "搜索条件")
    })
    public ResultDataUtil<Object> searchEntityArchives(@RequestParam Integer num, @RequestParam Integer size, String searchThings) {
        DataCommonVo dataCommonVo = iMakeArchivesSearchService.searchEntityArchives(num, size, searchThings, (short) 2, (short) 1, this.getLoginUserId());
        return ResultDataUtil.ok("根据条件查询实体档案数据信息列表数据成功", dataCommonVo);
    }


    //根据条件查询实体库数据
    @Log(level = EnumLogLevel.TRACE, module = "档案检索", context = "根据条件查询电子档案数据信息列表")
    @GetMapping("/searchElectronicArchives")
    @ApiOperation(value = "根据条件查询电子档案数据信息列表", notes = "根据条件查询电子档案数据信息列表")
    public ResultDataUtil<Page<MakeArchivesSearchElectronicVo>> searchElectronic(@Validated MakeArchivesSearchDto makeArchivesSearchDto) {
        makeArchivesSearchDto.setSearchType((short) 1);
        makeArchivesSearchDto.setUserType((short) 1);
        makeArchivesSearchDto.setUserId(this.getLoginUserId());
        Page<MakeArchivesSearchElectronicVo> makeArchivesSearchElectronicVoPage = iMakeArchivesSearchService.listElectronicByKeyWord(makeArchivesSearchDto);
        return ResultDataUtil.ok("根据条件查询电子档案数据信息列表成功", makeArchivesSearchElectronicVoPage);
    }


    //根据条件查询库数据
    @Log(level = EnumLogLevel.TRACE, module = "档案检索", context = "全文检索档案列表")
    @GetMapping("/searchArchives")
    @ApiOperation(value = "全文检索档案列表", notes = "全文检索档案列表")
    public ResultDataUtil<Page<MakeArchivesSearchVo>> searchArchives(@Validated MakeArchivesSearchDto makeArchivesSearchDto) {
        makeArchivesSearchDto.setSearchType((short) 1);
        makeArchivesSearchDto.setUserType((short) 1);
        makeArchivesSearchDto.setUserId(this.getLoginUserId());
        Page<MakeArchivesSearchVo> makeArchivesSearchElectronicVoPage = iMakeArchivesSearchService.listArchivesByKeyWord(makeArchivesSearchDto);
        return ResultDataUtil.ok("全文检索档案列表成功", makeArchivesSearchElectronicVoPage);
    }


    //根据库id 表id 搜索内容 查询对应的文件
    @Log(level = EnumLogLevel.TRACE, module = "档案检索", context = "全文检索文件信息列表")
    @GetMapping("/searchArchivesFile")
    @ApiOperation(value = "全文检索文件信息列表", notes = "全文检索文件信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "searchThings", value = "搜索条件"),
            @ApiImplicitParam(name = "archivesLibId", value = "库id"),
            @ApiImplicitParam(name = "archivesDirectoryId", value = "表id")
    })
    public ResultDataUtil<Page<MakeArchivesSearchPojo>> searchArchivesFile(@RequestParam Integer num, @RequestParam Integer size,
                                                                         String searchThings,Integer archivesLibId,Integer archivesDirectoryId) {
        Page<MakeArchivesSearchPojo> makeArchivesSearchElectronicVoPage = iMakeArchivesSearchService.page(num, size, searchThings,archivesDirectoryId,archivesLibId);
        return ResultDataUtil.ok("全文检索文件信息列表成功", makeArchivesSearchElectronicVoPage);
    }

    @Log(level = EnumLogLevel.TRACE, module = "档案检索", context = "查询正式库档案库列表信息树")
    @GetMapping("/sys-archives-library")
    @ApiOperation(value = "查询正式库档案库列表信息树", notes = "查询正式库档案库列表信息树")
    public ResultDataUtil<List<TreeUtil>> findArchivesTree() {
        List<TreeUtil> treeNodeUtils;
        if (EnumAuthType.ADMIN.getValue().equals(this.getUserAuthType())) {
            treeNodeUtils = iSysArchivesLibraryService.listTree(TreeUtil.ROOT_PARENT_ID);
        } else {
            treeNodeUtils = iSysUserService.listSysArchivesLibraryTree(this.getLoginUserId(), TreeUtil.ROOT_PARENT_ID);
        }
        return ResultDataUtil.ok("查询档案库列表信息树成功", treeNodeUtils);
    }

    //根据档案库id  查询对应的字段
    @Log(level = EnumLogLevel.TRACE, module = "档案检索", context = "根据档案库id查询对应的字段")
    @GetMapping("/searchArchivesField")
    @ApiOperation(value = "查询对应的字段", notes = "查询对应的字段")
    @ApiImplicitParam(name = "archiveId", value = "库id")
    public ResultDataUtil<Object> searchArchives(@Validated Integer archiveId) {
        QueryWrapper<SysArchivesLibraryField> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("archives_library_id",archiveId);
        queryWrapper.ne("from_type",1);
        List<SysArchivesLibraryField> archivesLibraryFields = iSysArchivesLibraryFieldService.list(queryWrapper);
        return ResultDataUtil.ok("根查询对应的字段成功", archivesLibraryFields);
    }

    //根据条件检索数据
    @Log(level = EnumLogLevel.TRACE, module = "条件检索", context = "根据条件检索数据")
    @GetMapping("/findDatasBySomethings")
    @ApiOperation(value = "根据条件检索数据", notes = "根据条件检索数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "archiveId", value = "库id"),
            @ApiImplicitParam(name = "searchThings", value = "搜索条件")
    })
    public ResultDataUtil<Page<MakeArchivesSearchVo>> findDatasBySomethings(@RequestParam Integer num, @RequestParam Integer size, Integer archiveId, String searchThings) {
        Page<MakeArchivesSearchVo> makeArchivesSearchPojoPage = iMakeArchivesSearchService.findDatasBySomethings(num, size, archiveId, searchThings);
        return ResultDataUtil.ok("根据条件检索数据成功", makeArchivesSearchPojoPage);
    }

    //根据库id 表id 搜索内容 查询对应的文件
    @Log(level = EnumLogLevel.TRACE, module = "档案检索", context = "根据条件检索文件信息列表")
    @GetMapping("/searchArchivesFileByIds")
    @ApiOperation(value = "根据条件检索文件信息列表", notes = "根据条件检索文件信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "archivesLibId", value = "库id"),
            @ApiImplicitParam(name = "archivesDirectoryId", value = "表id")
    })
    public ResultDataUtil<Page<MakeArchivesSearchPojo>> searchArchivesFileByIds(@RequestParam Integer num, @RequestParam Integer size,
                                                                          Integer archivesLibId,Integer archivesDirectoryId) {
        Page<MakeArchivesSearchPojo> makeArchivesSearchElectronicVoPage = iMakeArchivesSearchService.searchArchivesFileByIds(num, size, archivesDirectoryId,archivesLibId);
        return ResultDataUtil.ok("根据条件检索文件信息列表成功", makeArchivesSearchElectronicVoPage);
    }


    //////////////////////////条件检索////////////////////////////////

    //条件检索的电子档案目录
    @Log(level = EnumLogLevel.TRACE, module = "条件检索", context = "查询档案收集档案库列表信息树")
    @GetMapping("/archivesLibrary")
    @ApiOperation(value = "查询档案收集档案库列表信息树", notes = "查询档案收集档案库列表信息树")
    public ResultDataUtil<List<TreeUtil>> findTree() {
        List<TreeUtil> treeNodeUtils = iSysArchivesLibraryService.listTree(-1);
        return ResultDataUtil.ok("查询档案收集档案库列表信息树成功", treeNodeUtils);
    }

    //定义查询字段
    @Log(level = EnumLogLevel.TRACE, module = "条件检索", context = "查询定义查询字段")
    @GetMapping("/findField")
    @ApiOperation(value = "查询定义查询字段", notes = "查询定义查询字段")
    public ResultDataUtil<Object> findField() {
        QueryWrapper<SysBaseFieldLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", "1");
        List<SysBaseFieldLibrary> sysArchivesLibraryFieldDefaults = iSysArchivesLibraryFieldDefaultService.list(queryWrapper);
        return ResultDataUtil.ok("查询定义查询字段成功", sysArchivesLibraryFieldDefaults);
    }

   /* //根据条件检索数据
    @Log(level = EnumLogLevel.TRACE, module = "条件检索", context = "根据条件检索数据")
    @GetMapping("/findElectronicsDatasBySomethings")
    @ApiOperation(value = "根据条件检索数据", notes = "根据条件检索数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "archiveId", value = "库id"),
            @ApiImplicitParam(name = "searchThings", value = "搜索条件")
    })
    public ResultDataUtil<Object> findElectronicsDatasBySomethings(@RequestParam Integer num, @RequestParam Integer size, Integer archiveId, String searchThings) {
        DataCommonVo dataCommonVo = iMakeArchivesSearchService.findElectronicsDatasBySomethings(num, size, archiveId, searchThings);
        return ResultDataUtil.ok("根据条件检索数据成功", dataCommonVo);
    }*/
}
