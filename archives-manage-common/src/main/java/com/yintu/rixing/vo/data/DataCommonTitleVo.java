package com.yintu.rixing.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/1/19 11:19:10
 * @Version: 1.0
 */
@Data
@ApiModel
public class DataCommonTitleVo {

    @ApiModelProperty(value = "表头属性值")
    private String prop;
    @ApiModelProperty(value = "表头属性名")
    private String label;

    @ApiModelProperty(value = "表头是否显示")
    private Boolean show;

    @ApiModelProperty(value = "表单数据类型id")
    private Integer typeId;
    @ApiModelProperty(value = "表单数据类型值")
    private String typeProp;
    @ApiModelProperty(value = "表单数据类型名")
    private String typeLabel;
    
    @ApiModelProperty(value = "表单是否为空")
    private Boolean notNull;


}
