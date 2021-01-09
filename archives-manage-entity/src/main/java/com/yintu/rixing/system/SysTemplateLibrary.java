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
 * 系统模板库表
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_template_library")
@ApiModel(value = "SysTemplateLibrary对象", description = "系统模板库表")
public class SysTemplateLibrary extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "父节点主键", position = 6)
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "模板库编号", position = 7)
    @TableField("number")
    private Integer number;

    @ApiModelProperty(value = "模板库名称", position = 8)
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "模板库分类 1.目录 2.库", position = 9)
    @TableField("type")
    private Short type;

    @ApiModelProperty(value = "模板库描述", position = 10)
    @TableField("description")
    private String description;


}
