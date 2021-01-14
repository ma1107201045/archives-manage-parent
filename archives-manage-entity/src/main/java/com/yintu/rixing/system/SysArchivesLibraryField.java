package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统档案库字段表
 * </p>
 *
 * @author mlf
 * @since 2021-01-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_archives_library_field")
@ApiModel(value = "SysArchivesLibraryField对象", description = "系统档案库字段表")
public class SysArchivesLibraryField extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字段名称（注释或者描述）")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "key（定义数据库字段名）")
    @TableField("data_key")
    private String dataKey;

    @ApiModelProperty(value = "字段长度")
    @TableField("length")
    private Integer length;

    @ApiModelProperty(value = "字段是否必填 1.是 0.否")
    @TableField("required")
    private Integer required;

    @ApiModelProperty(value = "字段是否是索引 1.是 0.否")
    @TableField("index")
    private Integer index;

    @ApiModelProperty(value = "字段顺序")
    @TableField("order")
    private Integer order;

    @ApiModelProperty(value = "档案库id")
    @TableField("archives_library_id")
    private Integer archivesLibraryId;

    @ApiModelProperty(value = "模板库字段类型id")
    @TableField("template_library_field_type_id")
    private Integer templateLibraryFieldTypeId;

    @ApiModelProperty(value = "模板库字段对应类型", position = 14)
    @TableField(exist = false)
    private SysTemplateLibraryFieldType sysTemplateLibraryFieldType;

}
