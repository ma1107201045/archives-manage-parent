package com.yintu.rixing.archives;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.dto.archives.ArchQuantityStatisticsQueryDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ObjectConvertUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.archives.ArchQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchQuantityStatisticsQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/30 14:16:28
 * @Version: 1.0
 */
@RestController
@RequestMapping("/archives/arch-quantity-statistics")
@Api(tags = "查询数量统计")
@ApiSort(1)
public class ArchQuantityStatisticsController {

    @Autowired
    private IArchQuantityStatisticsService iArchQuantityStatisticsService;

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询数量统计档案库列表名称")
    @GetMapping("/archives-name")
    @ApiOperation(value = "查询数量统计档案库列表名称", notes = "查询数量统计档案库列表名称")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<List<ArchQuantityStatisticsQueryVo>> findArchivesName() {
        List<ArchQuantityStatisticsQueryVo> archQuantityStatisticsQueryVos = iArchQuantityStatisticsService.findArchivesName();
        return ResultDataUtil.ok("查询数量统计档案库列表名称成功", archQuantityStatisticsQueryVos);
    }

    @Log(level = EnumLogLevel.TRACE, module = "档案统计", context = "查询数量统计档案库统计数据")
    @GetMapping("/archives-quantity")
    @ApiOperation(value = "查询数量统计档案库统计数据", notes = "查询数量统计档案库统计数据")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<ArchQuantityStatisticsDataVo> findArchivesName(ArchQuantityStatisticsQueryDto archQuantityStatisticsQueryDto) {
        ArchQuantityStatisticsDataVo archQuantityStatisticsQueryVo = iArchQuantityStatisticsService.findArchivesData(archQuantityStatisticsQueryDto);
        return ResultDataUtil.ok("查询数量统计档案库统计数据成功", archQuantityStatisticsQueryVo);
    }

}
