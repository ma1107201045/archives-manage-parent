package com.yintu.rixing.dto.data;

import com.yintu.rixing.dto.common.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2021/1/19 10:58:39
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class DataCommonQueryDto extends PageDto {

    @ApiModelProperty(value = "档案库id", required = true)
    @NotNull
    private Integer archivesId;
}
