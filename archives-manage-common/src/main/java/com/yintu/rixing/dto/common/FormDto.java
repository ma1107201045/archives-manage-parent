package com.yintu.rixing.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2020/12/30 16:16:23
 * @Version: 1.0
 */
@Data
public class FormDto {

    @ApiModelProperty(value = "主键id(添加不需要带上，修改必须带上)")
    private Integer id;
}
