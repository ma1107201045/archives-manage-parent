package com.yintu.rixing.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2020/12/30 16:16:23
 * @Version: 1.0
 */
@Data
@ApiModel
public class IdDto {

    @ApiModelProperty(hidden = true)
    private Integer id;
}
