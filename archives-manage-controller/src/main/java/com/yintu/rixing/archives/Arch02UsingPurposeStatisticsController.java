package com.yintu.rixing.archives;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysDepartmentService;
import com.yintu.rixing.system.SysDepartment;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.archives.ArchArchivesQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchCommonVo;
import com.yintu.rixing.vo.archives.ArchUsingPurposeStatisticsDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/19 10:18:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("/archives/arch-using-purpose-statistics")
@Api(tags = "利用目的统计")
@ApiSort(2)
public class Arch02UsingPurposeStatisticsController {
    @Autowired
    private IArchUsingPurposeStatisticsService iArchUsingPurposeStatisticsService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询利用目的统计组织机构列表信息")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询利用目的统计组织机构列表信息", notes = "查询利用目的统计组织机构列表信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<List<SysDepartment>> findList() {
        List<SysDepartment> sysDepartments = iSysDepartmentService.list(new QueryWrapper<SysDepartment>().orderByDesc("id"));
        return ResultDataUtil.ok("查询利用目的统计组织机构列表信息成功", sysDepartments);
    }

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询利用目的统计-名称")
    @GetMapping("/archives-name")
    @ApiOperation(value = "查询利用目的统计-名称", notes = "查询利用目的统计-名称")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<List<ArchCommonVo>> findArchivesName() {
        List<ArchCommonVo> archCommonQueryVos = iArchUsingPurposeStatisticsService.findArchivesName();
        return ResultDataUtil.ok("查询利用目的统计-名称成功", archCommonQueryVos);
    }

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询利用目的统计-数据")
    @GetMapping("/data")
    @ApiOperation(value = "查询利用目的统计-数据", notes = "查询利用目的统计-数据")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<ArchUsingPurposeStatisticsDataVo> findArchivesName(@Validated ArchCommonQueryDto archCommonQueryDto) {
        ArchUsingPurposeStatisticsDataVo archUsingPurposeStatisticsDataVo = iArchUsingPurposeStatisticsService.findArchUsingPurposeStatisticsData(archCommonQueryDto);
        return ResultDataUtil.ok("查询利用目的统计-数据成功", archUsingPurposeStatisticsDataVo);
    }

}
