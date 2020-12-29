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
 *
 * </p>
 *
 * @author mlf
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_department")
@ApiModel(value = "SysUserDepartment对象", description = "系统用户部门中间表")
public class SysUserDepartment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "部门id")
    @TableField("department_id")
    private Integer departmentId;


}
