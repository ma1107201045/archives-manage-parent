package com.yintu.rixing.dto.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2021/2/27 16:49:41
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysDictionariesDetailsFormDto extends IdDto {

    @ApiModelProperty(value = "字典标签", required = true)
    @NotBlank
    private String label;

    @ApiModelProperty(value = "字典值", required = true)
    @NotBlank
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "排序")
    @TableField("order")
    private Integer order;

    @ApiModelProperty(value = "数据字典id", required = true)
    @NotNull
    @TableField("dictionaries_id")
    private Integer dictionariesId;
}
