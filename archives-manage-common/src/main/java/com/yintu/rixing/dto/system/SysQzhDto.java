package com.yintu.rixing.dto.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2020/11/26 18:40
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysQzhDto对象", description = "系统全宗号Dto")
public class SysQzhDto {

    @ApiModelProperty(value = "全宗号名称", required = true)
    @NotBlank
    private String qzhName;

    @ApiModelProperty(value = "全宗号", required = true)
    @NotBlank
    private String qzhNumber;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "是否是档案馆 1.是 0.否", required = true)
    @NotNull
    private Integer archivesCenter;

    @ApiModelProperty(value = "描述")
    private String description;
}
