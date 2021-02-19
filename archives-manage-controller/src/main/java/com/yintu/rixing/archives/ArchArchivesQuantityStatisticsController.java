package com.yintu.rixing.archives;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.archives.ArchCommonDataVo;
import com.yintu.rixing.vo.archives.ArchCommonQueryVo;
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
@ApiSort(1)
public class ArchArchivesQuantityStatisticsController {

    @Autowired
    private IArchArchivesQuantityStatisticsService iArchQuantityStatisticsService;

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询档案数量统计-名称")
    @GetMapping("/archives-name")
    @ApiOperation(value = "查询档案数量统计-名称", notes = "查询档案数量统计-名称")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<List<ArchCommonQueryVo>> findArchivesName() {
        List<ArchCommonQueryVo> archCommonQueryVos = iArchQuantityStatisticsService.findArchivesName();
        return ResultDataUtil.ok("查询档案数量统计-名称成功", archCommonQueryVos);
    }

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询档案数量统计-数据")
    @GetMapping("/data")
    @ApiOperation(value = "查询档案数量统计-数据", notes = "查询档案数量统计-数据")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<ArchCommonDataVo> findArchivesName(@Validated ArchCommonQueryDto archCommonQueryDto) {
        ArchCommonDataVo archCommonDataVo = iArchQuantityStatisticsService.findArchivesData(archCommonQueryDto);
        return ResultDataUtil.ok("查询档案数量统计-数据成功", archCommonDataVo);
    }

}
