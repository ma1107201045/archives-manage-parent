package com.yintu.rixing.index;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/24 16:54:40
 * @Version: 1.0
 */
@RestController
@RequestMapping("/index/inx-data-statistics")
@Api(tags = "数据统计接口")
@ApiSupport(order = 1)
public class InxDataStatisticsController {

    @Autowired
    private IInxDataStatisticsService iInxDataStatisticsService;

    @Log(level = EnumLogLevel.TRACE, module = "首页", context = "查询查询统计列表信息")
    @GetMapping
    @ApiOperation(value = "查询查询统计列表信息", notes = "查询查询统计列表信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<List<Long>> getInxDataStatistics() {
        List<Long> longs = iInxDataStatisticsService.findInxDataStatisticsData();
        return ResultDataUtil.ok("查询查询统计列表信息成功", longs);
    }
}
