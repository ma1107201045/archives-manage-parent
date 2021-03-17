package com.yintu.rixing.dto.data;

import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Author: xsf
 * @Date: 2021/3/13 17:50:13
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class DataCommonRollBackDto extends IdDto {

    @ApiModelProperty(value = "档案库id", required = true)
    @NotNull
    private Integer archivesLibraryId;

    @ApiModelProperty(value = "id", required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "回退备注", required = true)
    private String rollBackRemark;

}
