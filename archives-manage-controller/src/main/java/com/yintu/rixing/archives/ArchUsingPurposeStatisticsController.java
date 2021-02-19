package com.yintu.rixing.archives;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mlf
 * @Date: 2021/2/19 10:18:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("/archives/arch-using-purpose-statistics")
@Api(tags = "利用目的统计")
@ApiSort(2)
public class ArchUsingPurposeStatisticsController {
}
