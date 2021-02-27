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
 * 系统数据字典表
 * </p>
 *
 * @author mlf
 * @since 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dictionaries")
@ApiModel(value = "SysDictionaries对象", description = "系统数据字典表")
public class SysDictionaries extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典名称", position = 6)
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "描述", position = 7)
    @TableField("description")
    private String description;


}
