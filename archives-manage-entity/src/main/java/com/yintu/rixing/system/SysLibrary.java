package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.yintu.rixing.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mlf
 * @since 2020-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_library")
@ApiModel(value="SysLibrary对象", description="")
public class    SysLibrary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "库编号")
    @TableField("librarynumber")
    private Integer librarynumber;

    @ApiModelProperty(value = "父级id")
    @TableField("pid")
    private Integer pid;

    @ApiModelProperty(value = "库名称")
    @TableField("libraryname")
    private String libraryname;

    @ApiModelProperty(value = "表名")
    @TableField("tablename")
    private String tablename;

    @ApiModelProperty(value = "顺序号")
    @TableField("ordernumber")
    private Integer ordernumber;

    @ApiModelProperty(value = "描述")
    @TableField("describee")
    private String describee;

    @ApiModelProperty(value = "库类别")
    @TableField("libraryclasses")
    private String libraryclasses;



}
