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
 * 系统角色权限中间表
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_permission")
@ApiModel(value="SysRolePermission对象", description="系统角色权限中间表")
public class SysRolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Integer roleId;

    @ApiModelProperty(value = "权限id")
    @TableField("permission_id")
    private Integer permissionId;


}
