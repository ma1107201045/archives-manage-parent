package com.yintu.rixing.dto.data;

import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2021/1/19 16:00:38
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class DataArchivesLibraryFileFormDto extends IdDto {


    @ApiModelProperty(value = "原始文件名", required = true)
    @NotBlank
    private String originalName;

    @ApiModelProperty(value = "文件路径", required = true)
    @NotBlank
    private String path;

    @ApiModelProperty(value = "文件大小", required = true)
    @NotNull
    private Double size;

    @ApiModelProperty(value = "文件大小单位（B、KB、MB、GB）", required = true)
    @NotBlank
    private String unit;

    @ApiModelProperty(value = "文件名", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "请求地址", required = true)
    private String requestMapping;

    @ApiModelProperty(value = "文件类型 1.电子文件 2.扫描文件", required = true)
    @NotNull
    private Short type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "档案库id", required = true)
    @NotNull
    private String archivesLibraryId;

    @ApiModelProperty(value = "动态表id", required = true)
    @NotNull
    private Integer dataId;

}
