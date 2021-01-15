package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 系统模板库字段表
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_template_library_field")
@ApiModel(value = "SysTemplateLibraryField对象", description = "系统模板库字段表")
public class SysTemplateLibraryField extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字段名称（注释或者描述）", position = 6)
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "key（定义数据库字段名）", position = 7)
    @TableField("data_key")
    private String dataKey;

    @ApiModelProperty(value = "字段长度", required = true, position = 8)
    @TableField("length")
    private Integer length;

    @ApiModelProperty(value = "字段是否必填 1.是 0.否", position = 9)
    @TableField("required")
    private Short required;

    @ApiModelProperty(value = "字段是否是索引 1.是 0.否", position = 10)
    @TableField("`index`")
    private Short index;

    @ApiModelProperty(value = "字段顺序", position = 11)
    @TableField("`order`")
    private Integer order;

    @ApiModelProperty(value = "模板库id", position = 12)
    @TableField("template_library_id")
    private Integer templateLibraryId;

    @ApiModelProperty(value = "模板库字段类型id", position = 13)
    @TableField("template_library_field_type_id")
    private Integer templateLibraryFieldTypeId;

    @ApiModelProperty(value = "模板库字段对应类型", position = 14)
    @TableField(exist = false)
    private SysTemplateLibraryFieldType sysTemplateLibraryFieldType;
}
