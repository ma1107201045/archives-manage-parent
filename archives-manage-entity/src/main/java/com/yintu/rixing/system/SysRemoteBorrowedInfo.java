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
@ApiModel(value="SysRemoteBorrowedInfo对象", description="系统远程借阅记录表")
public class SysRemoteBorrowedInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "借阅内容")
    @TableField("context")
    private String context;

    @ApiModelProperty(value = "借阅目的")
    @TableField("goal")
    private String goal;

    @ApiModelProperty(value = "借阅类型 1.电子借阅 2.试题借阅")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "借阅开始日期")
    @TableField("strart_date")
    private Date strartDate;

    @ApiModelProperty(value = "借阅结束日期")
    @TableField("end_date")
    private Date endDate;

    @ApiModelProperty(value = "远程用户id")
    @TableField("remote_user_id")
    private Integer remoteUserId;

    @ApiModelProperty(value = "借阅实物id")
    @TableField("data_id")
    private Integer dataId;


}
