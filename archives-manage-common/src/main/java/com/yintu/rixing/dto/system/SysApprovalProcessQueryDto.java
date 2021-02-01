package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.PageDto;
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
    private String name;

    @ApiModelProperty(value = "审批类型 1.本地审批 2.远程审批")
    private Short approvalType;
}
