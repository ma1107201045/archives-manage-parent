package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2020/12/30 16:42:16
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysUserPasswordDto extends IdDto {

    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank
    private String newPassword;

}
