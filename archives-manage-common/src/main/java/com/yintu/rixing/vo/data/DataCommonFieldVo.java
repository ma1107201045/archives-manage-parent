package com.yintu.rixing.vo.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2021/1/19 11:19:10
 * @Version: 1.0
 */
@Data
@ApiModel
public class DataCommonFieldVo {

    @ApiModelProperty(value = "表头属性值")
    private String prop;
    @ApiModelProperty(value = "表头属性名")
    private String label;

    @ApiModelProperty(value = "表单值数据类型id")
    private Integer typeId;
    @ApiModelProperty(value = "表单值数据类型值")
    private String typeProp;
    @ApiModelProperty(value = "表单值数据类型名")
    private String typeLabel;
    @ApiModelProperty(value = "表单值是否为空")
    private Boolean notNull;


    @ApiModelProperty(value = "数据选项(数据类型为下拉框，则存储json串作为选项)")
    private String dataOptions;

    @ApiModelProperty(value = "字段是否在页面查询 1.是 0.否")
    private Boolean query;

    @ApiModelProperty(value = "字段是否在页面表头显示 1.是 0.否")
    private Boolean title;

    @ApiModelProperty(value = "字段是否在页面表单显示 1.是 0.否")
    private Boolean form;


}
