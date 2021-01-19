package com.yintu.rixing.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/1/15 10:50:31
 * @Version: 1.0
 */
@Data
@ApiModel
public class CommAuthorityVo {

    @ApiModelProperty(value = "主键id", position = 1)
    private Integer id;
    @ApiModelProperty(value = "权限名称", position = 2)
    private String name;
    @ApiModelProperty(value = "相对地址", position = 3)
    private String url;
    @ApiModelProperty(value = "请求方法", position = 4)
    private String method;
    @ApiModelProperty(value = "授权名称", position = 5)
    private String authorizedName;
}
