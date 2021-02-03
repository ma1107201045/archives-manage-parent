package com.yintu.rixing.make;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.system.SysTemplateLibraryFieldType;
import com.yintu.rixing.system.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 利用中心的借阅申请表
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("make_borrow")
@ApiModel(value="UtilizationCenterBorrow对象", description="利用中心的借阅申请表")
public class MakeBorrow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件id")
    @TableField("fileid")
    private Integer fileid;

    @ApiModelProperty(value = "利用目的id")
    @TableField("make_id")
    private Integer makeId;

    @ApiModelProperty(value = "内部和远程人员区分 1：内部   2：远程")
    @TableField("user_type")
    private Integer userType;

    @ApiModelProperty(value = "预览类型 0：不可预览  1：可预览")
    @TableField("preview_type")
    private Integer previewType;

    @ApiModelProperty(value = "借阅人id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "审核状态 1.正常 2.审核中 3.通过 4.拒绝")
    @TableField("audit_status")
    private Integer auditStatus;

    @ApiModelProperty(value = "审核完成时间")
    @TableField("audit_finish_time")
    private Date auditFinishTime;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "借阅类型 1：电子借阅  2：实体借阅")
    @TableField("borrow_type")
    private Integer borrowType;

    @ApiModelProperty(value = "借阅开始时间")
    @TableField("borrow_start_time")
    private Date borrowStartTime;

    @ApiModelProperty(value = "借阅结束时间")
    @TableField("borrow_end_time")
    private Date borrowEndTime;

    @ApiModelProperty(value = "借阅人员")
    @TableField(exist = false)
    private SysUser sysUser;
}
