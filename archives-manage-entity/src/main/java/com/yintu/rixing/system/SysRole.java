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
 * 系统角色表
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
@ApiModel(value="SysRole对象", description="系统角色表")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;


}
