package com.yintu.rixing.archives;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mlf
 * @Date: 2021/2/20 18:47:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("/archives/arch-custom-statistics-context")
@Api(tags = "自定义统计内容")
@ApiSort(5)
public class Arch05CustomStatisticsContextController {
}
