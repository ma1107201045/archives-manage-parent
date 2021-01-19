package com.yintu.rixing.dto.data;

import com.yintu.rixing.dto.common.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/18 14:42:13
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class DataCommonFormDto extends IdDto {

    @ApiModelProperty(value = "档案库id", required = true)
    @NotNull
    private Integer archivesId;

    @ApiModelProperty(value = "档案库参数集")
    private Map<String, String> params;
}
