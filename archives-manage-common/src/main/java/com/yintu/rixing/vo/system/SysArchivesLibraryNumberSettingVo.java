package com.yintu.rixing.vo.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/3/9 17:35:10
 * @Version: 1.0
 */
@Data
@ApiModel
public class SysArchivesLibraryNumberSettingVo {

    @ApiModelProperty(value = "档案库字名称", position = 1)
    private String archivesLibraryFieldName;

    @ApiModelProperty(value = "分隔符 1.- 2.·3..4.~5.=6./7.`", position = 2)
    private String separator;

    @ApiModelProperty(value = "自定义字符", position = 3)
    private String customCharacter;
}
