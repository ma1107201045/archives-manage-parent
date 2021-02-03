package com.yintu.rixing.make;

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
 * 借阅申请文件和审核人中间表
 * </p>
 *
 * @author mlf
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("make_borrow_auditor")
@ApiModel(value="MakeBorrowAuditor对象", description="借阅申请文件和审核人中间表")
public class MakeBorrowAuditor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "借阅记录id")
    @TableField("make_borrow_id")
    private Integer makeBorrowId;

    @ApiModelProperty(value = "审核人id")
    @TableField("auditor_id")
    private Integer auditorId;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "当前人是否在审核中 1.是 0.否")
    @TableField("activate")
    private Integer activate;

    @ApiModelProperty(value = "审核内容")
    @TableField("context")
    private String context;

    @ApiModelProperty(value = "附件名")
    @TableField("accessory_name")
    private String accessoryName;

    @ApiModelProperty(value = "附件路径")
    @TableField("accessory_path")
    private String accessoryPath;

    @ApiModelProperty(value = "是否处理1.已处理 0.待处理")
    @TableField("is_dispose")
    private Integer isDispose;

    @ApiModelProperty(value = "2.审核中 3.通过 4.拒绝 5.转交")
    @TableField("audit_status")
    private Integer auditStatus;

    @ApiModelProperty(value = "审核完成时间")
    @TableField("audit_finish_time")
    private Date auditFinishTime;


}
