package com.yintu.rixing.vo.common;

import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/19 11:57:49
 * @Version: 1.0
 */
@Data
@ApiModel
public class CommPermissionVo {

    @ApiModelProperty(value = "菜单集", position = 1)
    private List<TreeUtil> menu;

    @ApiModelProperty(value = "授权集", position = 2)
    private List<CommAuthorityVo> authorities;
}
