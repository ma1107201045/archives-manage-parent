package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 系统远程借阅记录表
 * </p>
 *
 * @author mlf
 * @since 2021-02-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_remote_borrowed_info")
@ApiModel(value = "SysRemoteBorrowedInfo对象", description = "系统远程借阅记录表")
public class SysRemoteBorrowedInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "借阅内容", position = 6)
    @TableField("context")
    private String context;

    @ApiModelProperty(value = "借阅目的", position = 7)
    @TableField("goal")
    private String goal;

    @ApiModelProperty(value = "借阅类型 1.电子借阅 2.试题借阅", position = 8)
    @TableField("type")
    private Short type;

    @ApiModelProperty(value = "借阅开始日期", position = 9)
    @TableField("start_date")
    private Date startDate;

    @ApiModelProperty(value = "借阅结束日期", position = 10)
    @TableField("end_date")
    private Date endDate;

    @ApiModelProperty(value = "审核状态 1.审核中 2.审核通过 3.审核拒绝", position = 11)
    @TableField("audit_status")
    private Short auditStatus;

    @ApiModelProperty(value = "审核完成时间", position = 12)
    @TableField("audit_time")
    private Date auditTime;

    @ApiModelProperty(value = "远程用户id", position = 13)
    @TableField("remote_user_id")
    private Integer remoteUserId;

    @ApiModelProperty(value = "借阅实物id", position = 13)
    @TableField("data_id")
    private Integer dataId;

}
