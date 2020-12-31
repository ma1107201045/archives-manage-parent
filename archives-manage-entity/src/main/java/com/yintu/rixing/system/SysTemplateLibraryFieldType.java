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
 * 系统模板库字段类型表
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_template_library_field_type")
@ApiModel(value = "SysTemplateLibraryFieldType对象", description = "系统模板库字段类型表")
public class SysTemplateLibraryFieldType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字段类型名称", position = 6)
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "定义数据库字段名", position = 7)
    @TableField("d_key")
    private String dKey;


}
