package com.yintu.rixing.archives;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mlf
 * @Date: 2021/2/19 17:09:27
 * @Version: 1.0
 */
@RestController
@RequestMapping("/archives/arch-archives-quantity-statistics")
@Api(tags = "档案借阅统计")
@ApiSort(3)
public class ArchArchivesBorrowStatisticsController {
}
