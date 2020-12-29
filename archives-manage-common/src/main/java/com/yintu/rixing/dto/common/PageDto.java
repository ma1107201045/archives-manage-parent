package com.yintu.rixing.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageDto {

    @ApiModelProperty(value = "页码", required = true)
    private Integer num;
    @ApiModelProperty(value = "页码值", required = true)
    private Integer size;

}
