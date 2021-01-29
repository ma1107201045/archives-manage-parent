package com.yintu.rixing.dto.remote;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2021/1/29 9:38:16
 * @Version: 1.0
 */
@Data
@ApiModel
public class RemoAuthenticationRegisterDto {

    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "查询人身份 1.学生 2.老师 3.员工 4.领导", required = true)
    @NotNull
    private Short searchIdentityId;

    @ApiModelProperty(value = "证件类型", required = true)
    @NotNull
    private Integer certificateType;

    @ApiModelProperty(value = "证件号码", required = true)
    @NotBlank
    private String certificateNumber;


    @ApiModelProperty(value = "学院/单位", required = true)
    @NotBlank
    private String school;

    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank
    private String phoneNum;

    @ApiModelProperty(value = "备注", position = 13)
    @TableField("remark")
    private String remark;
}
