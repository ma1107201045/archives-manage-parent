package com.yintu.rixing.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统远程用户表
 * </p>
 *
 * @author mlf
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_remote_user")
@ApiModel(value = "SysRemoteUser对象", description = "系统远程用户表")
public class SysRemoteUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名", position = 6)
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "查询人身份 1.学生 2.老师 3.员工 4.领导", position = 7)
    @TableField("search_identity_id")
    private Short searchIdentityId;

    @ApiModelProperty(value = "证件类型", position = 8)
    @TableField("certificate_type")
    private Integer certificateType;

    @ApiModelProperty(value = "证件号码", position = 9)
    @TableField("certificate_number")
    private String certificateNumber;

    @ApiModelProperty(value = "密码", position = 10)
    @TableField("password")
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    @ApiModelProperty(value = "学院/单位", position = 11)
    @TableField("school")
    private String school;

    @ApiModelProperty(value = "联系电话", position = 12)
    @TableField("phone_num")
    private String phoneNum;

    @ApiModelProperty(value = "备注", position = 13)
    @TableField("remark")
    private String remark;


}
