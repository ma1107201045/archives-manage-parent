package com.yintu.rixing.data;

import com.yintu.rixing.config.other.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 数据销毁库 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:19:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-destruction-library")
@Api(tags = "销毁库接口")
@ApiSort(6)
public class Data06DestructionLibraryController extends Authenticator {
}
