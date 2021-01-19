package com.yintu.rixing.common;

import com.yintu.rixing.config.other.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mlf
 * @Date: 2021/1/19 16:33:15
 * @Version: 1.0
 */
@RestController
@RequestMapping("/common/comm-file_upload")
@Api(tags = "文件上传接口")
@ApiSort(2)
public class CommFileController extends Authenticator {


}
