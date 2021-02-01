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
 * 系统审批流程表
 * </p>
 *
 * @author mlf
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_approval_process")
@ApiModel(value = "SysApprovalProcess对象", description = "系统审批流程表")
public class SysApprovalProcess extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配置名称", position = 6)
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "审批类型 1.本地审批 2.远程审批", position = 7)
    @TableField("approval_type")
    private Short approvalType;

    @ApiModelProperty(value = "是否审批 1.是 0.否", position = 8)
    @TableField("approval")
    private Short approval;


}
