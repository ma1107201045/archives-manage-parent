package com.yintu.rixing.warehouse;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.yintu.rixing.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.system.SysTemplateLibraryFieldType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 库房管理实体库字段表
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ware_template_library_field")
@ApiModel(value="WareTemplateLibraryField对象", description="库房管理实体库字段表")
public class WareTemplateLibraryField extends BaseEntity {

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
    @TableField("`index`")
    private Integer index;

    @ApiModelProperty(value = "字段顺序")
    @TableField("`order`")
    private Integer order;

    @ApiModelProperty(value = "模板库字段类型id")
    @TableField("template_library_field_type_id")
    private Integer templateLibraryFieldTypeId;

    @ApiModelProperty(value = "类型区分 0：未选  1：已选")
    @TableField("typeid")
    private Integer typeId;

    @ApiModelProperty(value = "字段是否在页面查询 1.是 0.否" )
    @TableField("query")
    private Short query;

    @ApiModelProperty(value = "字段是否在页面表头显示 1.是 0.否" )
    @TableField("title")
    private Short title;

    @ApiModelProperty(value = "字段是否在页面表单显示 1.是 0.否" )
    @TableField("form")
    private Short form;

    @ApiModelProperty(value = "模板库字段对应类型")
    @TableField(exist = false)
    private SysTemplateLibraryFieldType sysTemplateLibraryFieldType;


}
