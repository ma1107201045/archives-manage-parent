package com.yintu.rixing.archives;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.archives.ArchArchivesBorrowStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchArchivesQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchCommonVo;
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
 * @Date: 2021/2/19 17:09:27
 * @Version: 1.0
 */
@RestController
@RequestMapping("/archives/arch-archives-borrow-statistics")
@Api(tags = "档案借阅统计")
@ApiSort(3)
public class ArchArchivesBorrowStatisticsController {
    @Autowired
    private IArchArchivesBorrowStatisticsService iArchArchivesBorrowStatisticsService;

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询档案借阅统计-名称")
    @GetMapping("/archives-name")
    @ApiOperation(value = "查询档案数量统计-名称", notes = "查询档案借阅统计-名称")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<List<ArchCommonVo>> findArchivesName() {
        List<ArchCommonVo> archArchivesQuantityStatisticsQueryVos = iArchArchivesBorrowStatisticsService.findArchivesName();
        return ResultDataUtil.ok("查询档案借阅统计-名称成功", archArchivesQuantityStatisticsQueryVos);
    }

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询档案借阅统计-数据")
    @GetMapping("/data")
    @ApiOperation(value = "查询档案借阅统计-数据", notes = "查询档案借阅统计-数据")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<ArchArchivesBorrowStatisticsDataVo> findArchivesName(@Validated ArchCommonQueryDto archCommonQueryDto) {
        ArchArchivesBorrowStatisticsDataVo archArchivesBorrowStatisticsDataVo = iArchArchivesBorrowStatisticsService.findArchivesBorrowStatisticsData(archCommonQueryDto);
        return ResultDataUtil.ok("查询档案借阅统计-数据成功", archArchivesBorrowStatisticsDataVo);
    }
}
