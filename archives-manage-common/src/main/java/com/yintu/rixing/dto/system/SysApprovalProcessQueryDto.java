package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.common.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2021/1/16 10:20:58
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysApprovalProcessQueryDto extends PageDto {

    @ApiModelProperty(value = "配置名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "审批模式 1.固定 2.灵活")
    @NotNull
    private Short approvalModel;
}
