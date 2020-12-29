package com.yintu.rixing.dto.system;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class SysUserFormDto {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
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

    @ApiModelProperty(value = "用户类型 0.普通用户 1.管理员用户", required = true)
    @NotNull
    private Short authType;

    @ApiModelProperty(value = "角色id集", required = true)
    @NotNull
    private Set<Integer> roleIds;

}
