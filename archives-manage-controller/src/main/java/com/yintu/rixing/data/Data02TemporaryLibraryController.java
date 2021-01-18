package com.yintu.rixing.data;

import com.yintu.rixing.config.other.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 数据临时库 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:12:39
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-temporary-library")
@Api(tags = "临时库接口")
@ApiSort(2)
public class Data02TemporaryLibraryController extends Authenticator {
}
