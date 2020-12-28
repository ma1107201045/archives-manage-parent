package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.BaseEntity;
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
 * @since 2020-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_template")
@ApiModel(value="SysTemplate", description="档案模板管理表")
public class SysTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "模板名称")
    @TableField("tepname")
    private String tepname;
    @ApiModelProperty(value = "父id")
    @TableField("pid")
    private Integer pid;

    @ApiModelProperty(value = "管理模式")
    @TableField("management")
    private String management;

    @ApiModelProperty(value = "模板描述")
    @TableField("tepdescribe")
    private String tepdescribe;

    @ApiModelProperty(value = "对应的模板表名称")
    @TableField("tablename")
    private String tablename;

}
