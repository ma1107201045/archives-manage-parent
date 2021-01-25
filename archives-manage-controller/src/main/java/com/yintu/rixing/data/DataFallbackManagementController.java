package com.yintu.rixing.data;

import com.yintu.rixing.config.other.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 数据回退管理 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:21:11
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-fallback-management")
@Api(tags = "回退管理接口")
@ApiSort(6)
public class DataFallbackManagementController extends Authenticator {
}
