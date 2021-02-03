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
 * 系统审批流程配置中间表
 * </p>
 *
 * @author mlf
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_approval_process_configuration")
@ApiModel(value = "SysApprovalProcessConfiguration对象", description = "系统审批流程配置中间表")
public class SysApprovalProcessConfiguration extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "审批流程id")
    @TableField("approval_process_id")
    private Integer approvalProcessId;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Integer roleId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "顺序")
    @TableField("`order`")
    private Integer order;


}
