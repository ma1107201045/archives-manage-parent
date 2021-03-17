package com.yintu.rixing.dto.data;

import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @Author: xsf
 * @Date: 2021/3/13 16:03:13
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class DataCommonMarkDto extends IdDto {

    @ApiModelProperty(value = "档案库id", required = true)
    @NotNull
    private Integer archivesLibraryId;

    @ApiModelProperty(value = "id", required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "标记备注", required = true)
    private String diseaseRemark;

    @ApiModelProperty(value = "标记类型(1:设为病档;0:取消病档)", required = true)
    @NotNull
    private Integer type;

}
