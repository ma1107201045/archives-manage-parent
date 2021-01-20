package com.yintu.rixing.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

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
