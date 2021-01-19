package com.yintu.rixing.dto.data;

import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mlf
 * @Date: 2021/1/19 16:00:38
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class DataArchivesLibraryFileFormDto extends IdDto {

    @ApiModelProperty(value = "原始文件名")
    private String originalName;

    @ApiModelProperty(value = "文件路径")
    private String path;

    @ApiModelProperty(value = "文件大小（MB）")
    private Double size;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "请求地址")
    private String requestMapping;

    @ApiModelProperty(value = "文件类型 1.电子文件 2.扫描文件")
    private Integer type;

    @ApiModelProperty(value = "备注")
    private String remark;
}
