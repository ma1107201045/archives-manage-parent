package com.yintu.rixing.security;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import com.yintu.rixing.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 安全数据备份表
 * </p>
 *
 * @author mlf
 * @since 2021-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sec_data_backup")
@ApiModel(value = "SecDataBackup对象", description = "安全数据备份表")
public class SecDataBackup extends IdEntity {

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

    @ApiModelProperty(value = "备份名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "备份时间")
    @TableField("backup_time")
    private Date backupTime;

    @ApiModelProperty(value = "备份路径")
    @TableField("backup_path")
    private String backupPath;

    @ApiModelProperty(value = "备份文件大小（MB）")
    @TableField("backup_file_size")
    private Double backupFileSize;

    @ApiModelProperty(value = "请求映射")
    @TableField("request_mapping")
    private String requestMapping;

    @ApiModelProperty(value = "还原时间")
    @TableField("recovery_time")
    private Date recoveryTime;

    @ApiModelProperty(value = "表个数")
    @TableField("table_count")
    private Integer tableCount;

    @ApiModelProperty(value = "记录数")
    @TableField("record_count")
    private Integer recordCount;

    @ApiModelProperty(value = "记录大小")
    @TableField("record_size")
    private Double recordSize;


}
