package com.yintu.rixing.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2020/12/30 16:16:23
 * @Version: 1.0
 */
@Data
@ApiModel
public class PageDto {

    @ApiModelProperty(value = "页码", required = true)
    @NotNull
    private Integer num;
    @ApiModelProperty(value = "页码值", required = true)
    @NotNull
    private Integer size;

}
