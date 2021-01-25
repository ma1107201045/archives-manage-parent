package com.yintu.rixing.data;

import com.yintu.rixing.config.other.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 数据档案销毁 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:24:45
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-archives-destruction")
@Api(tags = "档案销毁接口")
@ApiSort(8)
public class DataArchivesDestructionController extends Authenticator {
}
