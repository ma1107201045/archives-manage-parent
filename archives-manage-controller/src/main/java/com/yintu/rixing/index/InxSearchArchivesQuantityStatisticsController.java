package com.yintu.rixing.index;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mlf
 * @Date: 2021/2/25 14:11:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("/index/inx-search-archives-quantity-statistics")
@Api(tags = "数据统计接口")
@ApiSupport(order = 1)
public class InxSearchArchivesQuantityStatisticsController {
}
