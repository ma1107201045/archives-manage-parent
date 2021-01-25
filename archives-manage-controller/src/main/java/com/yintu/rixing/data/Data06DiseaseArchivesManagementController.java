package com.yintu.rixing.data;

import com.yintu.rixing.config.other.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiSort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 数据病档管理 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:22:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/disease-archives-management")
@Api(tags = "病档管理接口")
@ApiSort(6)
public class Data06DiseaseArchivesManagementController extends Authenticator {
}
