package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="SysLog对象", description="系统日志表")
public class SysLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "日志内容")
    @TableField("context")
    private String context;


}
