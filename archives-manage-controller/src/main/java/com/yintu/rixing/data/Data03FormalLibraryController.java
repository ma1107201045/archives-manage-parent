package com.yintu.rixing.data;

import com.yintu.rixing.config.other.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 数据正式库 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:14:15
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-formal-library")
@Api(tags = "正式库接口")
@ApiSort(3)
public class Data03FormalLibraryController extends Authenticator {
}
