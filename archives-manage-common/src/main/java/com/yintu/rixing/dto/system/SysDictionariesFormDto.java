package com.yintu.rixing.dto.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.dto.base.IdDto;
import com.yintu.rixing.dto.base.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @Author: mlf
 * @Date: 2021/2/27 16:36:28
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysDictionariesFormDto extends IdDto {


    @ApiModelProperty(value = "字典名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;
}
