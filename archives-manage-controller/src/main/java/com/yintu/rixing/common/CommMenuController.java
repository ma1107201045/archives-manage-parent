package com.yintu.rixing.common;

import com.yintu.rixing.config.other.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mlf
 * @Date: 2021/1/4 10:32:14
 * @Version: 1.0
 */
@RestController
@RequestMapping("/common/comm-department")
@Api(tags = "用户菜单接口")
@ApiSort(1)
public class CommMenuController extends Authenticator {


}
