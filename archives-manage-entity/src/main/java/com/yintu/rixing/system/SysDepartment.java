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
 * 部门表
 * </p>
 *
 * @author mlf
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_department")
@ApiModel(value = "SysDepartment对象", description = "系统部门表")
public class SysDepartment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父节点主键", position = 6)
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "部门名称", position = 7)
    @TableField("name")
    private String name;


}
