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
 * 编研和审核人中间表
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("make_compilation_auditor")
@ApiModel(value="MakeCompilationAuditor对象", description="编研和审核人中间表")
public class MakeCompilationAuditor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编研id")
    @TableField("make_compilation_id")
    private Integer makeCompilationId;

    @ApiModelProperty(value = "审核人id")
    @TableField("auditor_id")
    private Integer auditorId;

    @ApiModelProperty(value = "审核内容")
    @TableField("context")
    private String context;

    @ApiModelProperty(value = "附件名")
    @TableField("accessory_name")
    private String accessoryName;

    @ApiModelProperty(value = "附件路径")
    @TableField("accessory_path")
    private String accessoryPath;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "当前人是否在审核中 1.是 0.否")
    @TableField("activate")
    private Integer activate;

    @ApiModelProperty(value = "是否处理1.已处理 0.待处理")
    @TableField("is_dispose")
    private Integer isDispose;

    @ApiModelProperty(value = "1.审核中 2.通过 3.拒绝 4.转交")
    @TableField("audit_status")
    private Integer auditStatus;

    @ApiModelProperty(value = "审核完成时间")
    @TableField("audit_finish_time")
    private Date auditFinishTime;


}
