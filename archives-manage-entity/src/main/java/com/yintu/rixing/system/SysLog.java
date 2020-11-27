package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.javafx.geom.transform.Identity;
import com.yintu.rixing.BaseEntity;
import com.yintu.rixing.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_log")
@ApiModel(value = "SysLog对象", description = "系统日志表")
public class SysLog extends IdEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "操作人")
    @TableField("operator")
    private String operator;

    @ApiModelProperty(value = "日志级别 1.TRACE < 2.DEBUG < 3.INFO < 4.WARN < 5.ERROR")
    @TableField("level")
    private Short level;

    @ApiModelProperty(value = "日志记录模块名称")
    @TableField("module")
    private String module;

    @ApiModelProperty(value = "日志内容")
    @TableField("context")
    private String context;

    @ApiModelProperty(value = "日志描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "登录ip")
    @TableField("login_ip")
    private String loginIp;

}
