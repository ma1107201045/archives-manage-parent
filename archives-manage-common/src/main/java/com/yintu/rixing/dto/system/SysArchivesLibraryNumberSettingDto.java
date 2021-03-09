package com.yintu.rixing.dto.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2021/3/9 15:20:14
 * @Version: 1.0
 */
@Data
@ApiModel
public class SysArchivesLibraryNumberSettingDto {

    @ApiModelProperty(value = "档案库id", required = true)
    @NotNull
    private Integer archivesLibraryId;

    @ApiModelProperty(value = "分隔符 1.- 2.·3..4.~5.=6./7.`")
    @NotNull
    private String separator;

    @ApiModelProperty(value = "档案库字段id")
    private Integer archivesLibraryFieldId;

    @ApiModelProperty(value = "自定义字符")
    private String customCharacter;
}
