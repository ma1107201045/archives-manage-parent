package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * (SysArchives)实体类
 *
 * @author makejava
 * @since 2020-12-17 18:45:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_archives")
@ApiModel(value = "SysArchives", description = "档案模板表")
public class SysArchives extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "中文名称")
    @TableField("chinesename")
    private String chinesename;

    @ApiModelProperty(value = "英文名称")
    @TableField("englishname")
    private String englishname;

    @ApiModelProperty(value = "标准字段")
    @TableField("standardfields")
    private String standardfields;

    @ApiModelProperty(value = "值类型")
    @TableField("valuetype")
    private String valuetype;

    @ApiModelProperty(value = "字段长度")
    @TableField("fieldlength")
    private Integer fieldlength;
    @ApiModelProperty(value = "顺序号")
    @TableField("ordernumber")
    private Integer ordernumber;
    @ApiModelProperty(value = "顺序号")
    @TableField("ordernumber")
    private String listconcealsign;

    private String redactconcealsign;

    @ApiModelProperty(value = "显示长度")
    @TableField("showlength")
    private Integer showlength;


}