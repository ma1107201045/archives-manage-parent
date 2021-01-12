package com.yintu.rixing.make;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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

    @ApiModelProperty(value = "借阅人姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "借阅人身份")
    @TableField("identity")
    private Integer identity;

    @ApiModelProperty(value = "证件类型")
    @TableField("certificate_type")
    private Integer certificateType;

    @ApiModelProperty(value = "证件号码")
    @TableField("certificate_number")
    private String certificateNumber;

    @ApiModelProperty(value = "学院或者单位")
    @TableField("college_or_unit")
    private String collegeOrUnit;

    @ApiModelProperty(value = "联系电话")
    @TableField("phone_number")
    private String phoneNumber;

    @ApiModelProperty(value = "查阅目的")
    @TableField("purpose")
    private String purpose;

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


}
