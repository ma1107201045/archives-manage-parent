package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/16 10:09:11
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysApprovalProcessFormDto extends IdDto {

    @ApiModelProperty(value = "配置名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "审批类型 1.本地审批 2.远程审批", required = true)
    @NotNull
    private Short approvalType;

    @ApiModelProperty(value = "是否审批 1.是 0.否", required = true)
    @NotNull
    private Short approval;

    @ApiModelProperty(value = "角色id集")
    private List<Integer> roleIds;

    @ApiModelProperty(value = "用户id集")
    private List<Integer> userIds;

    @ApiModelProperty(value = "顺序集")
    private List<Integer> orders;

}
