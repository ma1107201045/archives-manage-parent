package com.yintu.rixing.make;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;

import java.util.Date;

import com.yintu.rixing.vo.make.MakeBorrowAuditorVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 利用中心的借阅申请表
 * </p>
 *
 * @author mlf
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("make_borrow")
@ApiModel(value = "MakeBorrow对象", description = "利用中心的借阅申请表")
public class MakeBorrow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件id")
    @TableField("fileid")
    private Integer fileid;

    @ApiModelProperty(value = "利用目的id")
    @TableField("make_id")
    private Integer makeId;

    @ApiModelProperty(value = "借阅人id", position = 7)
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "借阅开始时间", position = 8)
    @TableField("borrow_start_time")
    private Date borrowStartTime;

    @ApiModelProperty(value = "借阅结束时间", position = 9)
    @TableField("borrow_end_time")
    private Date borrowEndTime;

    @ApiModelProperty(value = "内部和远程人员区分 1：内部 2：远程", position = 11)
    @TableField("user_type")
    private Short userType;

    @ApiModelProperty(value = "借阅类型 1：电子借阅 2：实体借阅", position = 12)
    @TableField("borrow_type")
    private Short borrowType;

    @ApiModelProperty(value = "备注", position = 13)
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "审核状态 1.正常 2.审核中 3.通过 4.拒绝", position = 14)
    @TableField("audit_status")
    private Short auditStatus;

    @ApiModelProperty(value = "审核完成时间", position = 15)
    @TableField("audit_finish_time")
    private Date auditFinishTime;

    @ApiModelProperty(value = "预览类型 0：不可预览  1：可预览", position = 16)
    @TableField("preview_type")
    private Short previewType;

    @ApiModelProperty(value = "审批流id")
    @TableField(exist = false)
    private Integer approvalId;


    @ApiModelProperty(value = "实体档案")
    @TableField(exist = false)
    private Map<String, Object> dataEntityFile;

    @ApiModelProperty(value = "审核人信息")
    @TableField(exist = false)
    private List<MakeBorrowAuditorVo> makeBorrowAuditorVos;
}
