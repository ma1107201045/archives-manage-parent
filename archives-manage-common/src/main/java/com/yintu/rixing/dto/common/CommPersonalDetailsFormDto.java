package com.yintu.rixing.dto.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * @Author: mlf
 * @Date: 2021/3/9 10:29:38
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class CommPersonalDetailsFormDto extends IdDto {

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank
    private String username;

    @ApiModelProperty(value = "密码")
    @Null
    private String password;

    @ApiModelProperty(value = "用户名称", required = true)
    @NotBlank
    private String nickname;

    @ApiModelProperty(value = "证件类型")
    private Short certificateType;

    @ApiModelProperty(value = "证件号码")
    private String certificateNumber;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    @Email
    private String email;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "电话或者手机号码")
    private String phone;
}
