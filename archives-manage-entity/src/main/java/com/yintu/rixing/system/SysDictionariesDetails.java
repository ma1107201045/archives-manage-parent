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
 * 系统数据字典详情表
 * </p>
 *
 * @author mlf
 * @since 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dictionaries_details")
@ApiModel(value="SysDictionariesDetails对象", description="系统数据字典详情表")
public class SysDictionariesDetails extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典标签")
    @TableField("label")
    private String label;

    @ApiModelProperty(value = "字典值")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "排序")
    @TableField("`order`")
    private Integer order;

    @ApiModelProperty(value = "数据字典id")
    @TableField("dictionaries_id")
    private Integer dictionariesId;


}
