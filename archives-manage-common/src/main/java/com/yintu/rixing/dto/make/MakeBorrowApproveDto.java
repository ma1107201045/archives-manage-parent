package com.yintu.rixing.dto.make;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2021/2/4 16:03:43
 * @Version: 1.0
 */
@Data
@ApiModel
public class MakeBorrowApproveDto {
    @ApiModelProperty(value = "主键id", required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "1.转交 2.通过 3.拒绝 ", required = true)
    @NotNull
    private Short auditStatus;

    @ApiModelProperty(value = " 转交人id ", required = true)
    private Integer auditorId;

    @ApiModelProperty(value = "审批内容")
    private String context;

    @ApiModelProperty(value = "附件名")
    private String accessoryName;

    @ApiModelProperty(value = "附件地址")
    private String accessoryPath;

}


