package com.yintu.rixing.make;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.make.MakeArchivesSearchDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysArchivesLibraryFieldDefaultService;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibraryFieldDefault;
import com.yintu.rixing.util.IdentityIdUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import com.yintu.rixing.vo.make.MakeArchivesSearchElectronicVo;
import com.yintu.rixing.warehouse.IWareTemplateLibraryFieldService;
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
import java.util.Objects;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:54:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("/make/make-archives-search")
@Api("档案检索接口")
@ApiSupport(order = 4)
public class MakeArchivesSearchController {
    @Autowired
    private IMakeArchivesSearchService iMakeArchivesSearchService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private ISysArchivesLibraryFieldDefaultService iSysArchivesLibraryFieldDefaultService;

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
        DataCommonVo dataCommonVo = iMakeArchivesSearchService.searchEntityArchives(num, size, searchThings);
        return ResultDataUtil.ok("根据条件查询实体档案数据信息列表数据成功", dataCommonVo);
    }


    //根据条件查询实体库数据
    @Log(level = EnumLogLevel.TRACE, module = "档案检索", context = "根据条件查询电子档案数据信息列表")
    @GetMapping("/searchElectronicArchives")
    @ApiOperation(value = "根据条件查询电子档案数据信息列表", notes = "根据条件查询电子档案数据信息列表")
    public ResultDataUtil<Page<MakeArchivesSearchElectronicVo>> searchElectronic(@Validated MakeArchivesSearchDto makeArchivesSearchDto) {
        makeArchivesSearchDto.setUserType((short) 1);
        makeArchivesSearchDto.setUserId(Objects.requireNonNull(Authenticator.getPrincipal()).getId());
        Page<MakeArchivesSearchElectronicVo> makeArchivesSearchElectronicVoPage = iMakeArchivesSearchService.listElectronicByKeyWord(makeArchivesSearchDto);
        return ResultDataUtil.ok("根据条件查询电子档案数据信息列表成功", makeArchivesSearchElectronicVoPage);
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
        QueryWrapper<SysArchivesLibraryFieldDefault> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", "1");
        List<SysArchivesLibraryFieldDefault> sysArchivesLibraryFieldDefaults = iSysArchivesLibraryFieldDefaultService.list(queryWrapper);
        return ResultDataUtil.ok("查询定义查询字段成功", sysArchivesLibraryFieldDefaults);
    }

    //根据条件检索数据
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
    }
}
