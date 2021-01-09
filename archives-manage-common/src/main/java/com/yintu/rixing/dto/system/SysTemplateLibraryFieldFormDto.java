package com.yintu.rixing.dto.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.dto.common.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * @Author: mlf
 * @Date: 2020/12/31 11:51:04
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysTemplateLibraryFieldFormDto对象", description = "系统模板库字段Dto")
public class SysTemplateLibraryFieldFormDto extends IdDto {

    @ApiModelProperty(value = "字段名称（注释或者描述）")
    private String name;

    @ApiModelProperty(value = "key（用于添加表的字段名）", required = true)
    @NotNull
    @Pattern(regexp = "^/w+$", message = "输入的key由数字、26个英文字母或者下划线组成")
    private String dataKey;

    @ApiModelProperty(value = "字段是否必填 1.是 0.否", required = true)
    @NotNull
    private Integer required;

    @ApiModelProperty(value = "字段是否是索引 1.是 0.否", required = true)
    @NotNull
    private Integer index;

    @ApiModelProperty(value = "字段顺序", required = true)
    @NotNull
    private Integer order;

    @ApiModelProperty(value = "模板库id", required = true)
    @NotNull
    private Integer templateLibraryId;

    @ApiModelProperty(value = "模板库字段类型id", required = true)
    @NotNull
    private Integer templateLibraryFieldTypeId;
}
