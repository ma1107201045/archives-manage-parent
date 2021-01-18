package com.yintu.rixing.data;

import com.yintu.rixing.config.other.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 数据回收站 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:18:27
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-recycle-bin")
@Api(tags = "回收站接口")
@ApiSort(5)
public class Data05RecycleBinController extends Authenticator {
}
