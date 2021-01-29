package com.yintu.rixing.dto.remote;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: mlf
 * @Date: 2021/1/29 9:31:28
 * @Version: 1.0
 */
@Data
@ApiModel
public class RemoAuthenticationLoginDto {

    @ApiModelProperty(value = "证件号码", required = true)
    @NotBlank
    private String certificateNumber;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
    private String password;
}
