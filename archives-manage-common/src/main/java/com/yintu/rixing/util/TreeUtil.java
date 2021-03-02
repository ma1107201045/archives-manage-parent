package com.yintu.rixing.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author:mlf
 * @date:2020/5/22 16:37
 */
@Data
@ApiModel(value = "TreeUtil对象", description = "返回树结构参数")
public class TreeUtil {
    public static final Integer ROOT_PARENT_ID = -1;

    @ApiModelProperty(value = "主键id")
    private Long id;
    @ApiModelProperty(value = "文本")
    private String label;
    @ApiModelProperty(value = "值")
    private String value;
    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "子节点")
    private List<TreeUtil> children;

    @ApiModelProperty(value = "其他参数")
    private Map<String, Object> a_attr;

}
