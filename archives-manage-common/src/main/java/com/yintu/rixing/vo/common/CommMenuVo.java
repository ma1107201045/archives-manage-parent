package com.yintu.rixing.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/1/4 10:45:04
 * @Version: 1.0
 */
@Data
@ApiModel
public class CommMenuVo {
    @ApiModelProperty(value = "主键id", position = 1)
    private Integer id;
    @ApiModelProperty(value = "权限名称", position = 2)
    private String name;
    @ApiModelProperty(value = "菜单项显示的图片地址", position = 3)
    private String iconCls;
    @ApiModelProperty(value = "前端路由转向", position = 4)
    private String path;
}
