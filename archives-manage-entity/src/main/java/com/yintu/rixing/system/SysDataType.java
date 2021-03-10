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
 * 系统数据类型表
 * </p>
 *
 * @author mlf
 * @since 2020-12-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_data_type")
@ApiModel(value = "SysDataType对象", description = "系统数据类型表")
public class SysDataType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字段类型名称", position = 6)
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "key（定义数据库字段名类型名）", position = 7)
    @TableField("data_key")
    private String dataKey;


}
