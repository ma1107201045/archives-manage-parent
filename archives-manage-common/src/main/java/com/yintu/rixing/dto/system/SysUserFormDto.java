package com.yintu.rixing.dto.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2020/12/29 13:31:47
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysUserFormDto extends IdDto {

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank
    private String username;

    @ApiModelProperty(value = "密码")
    //@NotBlank 添加必须传 修改不需要
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
    @Size(min = 1)
    private Set<Integer> roleIds;

    @ApiModelProperty(value = "部门id集", required = true)
    @NotNull
    @Size(min = 1)
    private Set<Integer> departmentIds;

}
