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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/30 14:16:28
 * @Version: 1.0
 */
@RestController
@RequestMapping("/archives/arch-archives-quantity-statistics")
@Api(tags = "档案数量统计")
@ApiSort(4)
public class Arch04ArchivesQuantityStatisticsController {

    @Autowired
    private IArchArchivesQuantityStatisticsService iArchQuantityStatisticsService;
    @Autowired
    private ISysDepartmentService iSysDepartmentService;

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询档案数量统计组织机构列表信息")
    @GetMapping("/sys-department")
    @ApiOperation(value = "查询档案数量统计组织机构列表信息", notes = "查询档案数量统计组织机构列表信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<List<SysDepartment>> findList() {
        List<SysDepartment> sysDepartments = iSysDepartmentService.list(new QueryWrapper<SysDepartment>().orderByDesc("id"));
        return ResultDataUtil.ok("查询档案数量统计组织机构列表信息成功", sysDepartments);
    }


    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询档案数量统计-名称")
    @GetMapping("/archives-name")
    @ApiOperation(value = "查询档案数量统计-名称", notes = "查询档案数量统计-名称")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<List<ArchCommonVo>> findArchivesName() {
        List<ArchCommonVo> archArchivesQuantityStatisticsQueryVos = iArchQuantityStatisticsService.findArchivesName();
        return ResultDataUtil.ok("查询档案数量统计-名称成功", archArchivesQuantityStatisticsQueryVos);
    }

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询档案数量统计-数据")
    @GetMapping("/data")
    @ApiOperation(value = "查询档案数量统计-数据", notes = "查询档案数量统计-数据")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<ArchArchivesQuantityStatisticsDataVo> findArchivesName(@Validated ArchCommonQueryDto archCommonQueryDto) {
        ArchArchivesQuantityStatisticsDataVo archArchivesQuantityStatisticsDataVo = iArchQuantityStatisticsService.findArchivesQuantityStatisticsData(archCommonQueryDto);
        return ResultDataUtil.ok("查询档案数量统计-数据成功", archArchivesQuantityStatisticsDataVo);
    }

}
