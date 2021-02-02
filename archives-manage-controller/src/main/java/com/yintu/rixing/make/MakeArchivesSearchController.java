package com.yintu.rixing.make;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:54:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("/make/make-archives-search")
@Api("档案检索接口")
@ApiSupport(order = 4)
public class MakeArchivesSearchController {
}
