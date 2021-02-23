package com.yintu.rixing.make;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.yintu.rixing.BaseEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 档案编研表
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("make_compilation")
@ApiModel(value="MakeCompilation对象", description="档案编研表")
public class MakeCompilation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "档案编研的树表id")
    @TableField("make_compilation_tree_id")
    private Integer makeCompilationTreeId;

    @ApiModelProperty(value = "编研人id")
    @TableField("editorId")
    private Integer editorid;

    @ApiModelProperty(value = "专题名称")
    @TableField("topicName")
    private String topicname;

    @ApiModelProperty(value = "主编")
    @TableField("mainEditor")
    private String maineditor;

    @ApiModelProperty(value = "参编")
    @TableField("participationEditor")
    private String participationeditor;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "编研内容")
    @TableField("editContext")
    private String editcontext;

    @ApiModelProperty(value = "审批意见")
    @TableField("auditView")
    private String auditview;

    @ApiModelProperty(value = "审批时间")
    @TableField("auditTime")
    private Date audittime;

    @ApiModelProperty(value = "审核状态 1.审核中 2.通过 3.拒绝")
    @TableField("audit_status")
    private Integer auditStatus;

    @ApiModelProperty(value = "审核完成时间")
    @TableField("audit_finish_time")
    private Date auditFinishTime;

    @ApiModelProperty(value = "是否审核 1：审核  0：未审核")
    @TableField("isnotAudit")
    private Integer isnotAudit;


}
