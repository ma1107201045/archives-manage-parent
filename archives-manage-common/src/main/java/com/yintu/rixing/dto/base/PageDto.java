package com.yintu.rixing.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PageDto {

    @ApiModelProperty(value = "页码", required = true)
    private Integer num;
    @ApiModelProperty(value = "页码值", required = true)
    private Integer size;

}
