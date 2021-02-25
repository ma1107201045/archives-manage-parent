package com.yintu.rixing.index;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.dto.index.InxUsingPurposeStatisticsQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.make.IMakeBorrowPurposeService;
import com.yintu.rixing.make.MakeBorrowPurpose;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.index.InxSearchArchivesQuantityStatisticsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/2/25 14:11:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("/index/inx-search-archives-quantity-statistics")
@Api(tags = "查档数量和利用目的统计接口")
@ApiSupport(order = 2)
public class InxSearchArchivesQuantityStatisticsAndUsingPurposeController {

    @Autowired
    private IInxSearchArchivesQuantityAndUsingPurposeStatisticsService iInxSearchArchivesQuantityAndUsingPurposeStatisticsService;
    @Autowired
    private IMakeBorrowPurposeService iMakeBorrowPurposeService;

    @Log(level = EnumLogLevel.TRACE, module = "首页", context = "查询查档数量统计列表信息")
    @GetMapping("/data1")
    @ApiOperation(value = "查询查档数量统计列表信息", notes = "查询查档数量统计列表信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<InxSearchArchivesQuantityStatisticsVo> getSearchArchivesQuantityStatisticsData() {
        InxSearchArchivesQuantityStatisticsVo inxSearchArchivesQuantityStatisticsVo = iInxSearchArchivesQuantityAndUsingPurposeStatisticsService.findSearchArchivesQuantityStatisticsData();
        return ResultDataUtil.ok("查询查档数量统计列表信息成功", inxSearchArchivesQuantityStatisticsVo);
    }

    @Log(level = EnumLogLevel.TRACE, module = "首页", context = "查询利用目的列表信息")
    @GetMapping("/data2")
    @ApiOperation(value = "查询利用目的统计列表信息", notes = "查询利用目的统计列表信息")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<List<Long>> getUsingPurposeData(InxUsingPurposeStatisticsQueryDto inxUsingPurposeStatisticsQueryDto) {
        List<Long> longs = iInxSearchArchivesQuantityAndUsingPurposeStatisticsService.findUsingPurposeData(inxUsingPurposeStatisticsQueryDto);
        return ResultDataUtil.ok("查询利用目的统计列表信息成功", longs);
    }

    @Log(level = EnumLogLevel.TRACE, module = "首页", context = "查询首页利用目的信息")
    @GetMapping("/make-borrow-purpose")
    @ApiOperation(value = "查询首页利用目的信息", notes = "查询首页利用目的信息")
    @ApiOperationSupport(order = 3)
    public ResultDataUtil<List<MakeBorrowPurpose>> getMakeBorrowPurposes() {
        List<MakeBorrowPurpose> makeBorrowPurposes = iMakeBorrowPurposeService.list().stream().sorted((makeBorrowPurpose1, makeBorrowPurpose2) -> Integer.compare(makeBorrowPurpose2.getId(), makeBorrowPurpose1.getId())).collect(Collectors.toList());
        return ResultDataUtil.ok("查询首页利用目的信息成功", makeBorrowPurposes);
    }
}
