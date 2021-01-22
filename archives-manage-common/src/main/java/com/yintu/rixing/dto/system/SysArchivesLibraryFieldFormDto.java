package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author: mlf
 * @Date: 2021/1/14 10:51:00
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysArchivesLibraryFieldFormDto extends IdDto {

    @ApiModelProperty(value = "字段名称（注释或者描述）")
    private String name;

    @ApiModelProperty(value = "key（用于添加表的字段名）", required = true)
    @NotNull
    @Pattern(regexp = "(^_([a-zA-Z0-9]_?)*$)|(^[a-zA-Z](_?[a-zA-Z0-9])*_?$)", message = "输入key首位可以是字母以及下划线。首位之后可以是字母，数字以及下划线。下划线后不能接下划线")
    private String dataKey;

    @ApiModelProperty(value = "字段长度", required = true)
    @NotNull
    private Integer length;

    @ApiModelProperty(value = "字段是否必填 1.是 0.否", required = true)
    @NotNull
    private Short required;

    @ApiModelProperty(value = "字段是否是索引 1.是 0.否", required = true)
    @NotNull
    private Short index;

    @ApiModelProperty(value = "字段顺序", required = true)
    @NotNull
    private Integer order;

    @ApiModelProperty(value = "字段是否在页面查询 1.是 0.否", required = true)
    @NotNull
    private Short query;

    @ApiModelProperty(value = "字段是否在页面表头显示 1.是 0.否", required = true)
    @NotNull
    private Short title;

    @ApiModelProperty(value = "字段是否在页面表单显示 1.是 0.否", required = true)
    @NotNull
    private Short form;

    @ApiModelProperty(value = "档案库id", required = true)
    @NotNull
    private Integer archivesLibraryId;

    @ApiModelProperty(value = "档案库字段类型id", required = true)
    @NotNull
    private Integer templateLibraryFieldTypeId;
}
