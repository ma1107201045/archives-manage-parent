package com.yintu.rixing.dto.data;

import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/19 16:00:38
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class DataArchivesLibraryFileFormDto extends IdDto {


    @ApiModelProperty(value = "原始文件名集", required = true)
    @NotNull
    @Size(min = 1)
    private List<String> originalNames;

    @ApiModelProperty(value = "文件路径集", required = true)
    @NotNull
    @Size(min = 1)
    private List<String> paths;

    @ApiModelProperty(value = "文件大小集", required = true)
    @NotNull
    @Size(min = 1)
    private List<Double> sizes;

    @ApiModelProperty(value = "文件大小单位（B、KB、MB、GB）集", required = true)
    @NotNull
    @Size(min = 1)
    private List<String> units;

    @ApiModelProperty(value = "文件名集", required = true)
    @NotNull
    @Size(min = 1)
    private List<String> names;

    @ApiModelProperty(value = "请求地址集", required = true)
    @NotNull
    @Size(min = 1)
    private List<String> requestMappings;

    @ApiModelProperty(value = "文件类型 1.电子文件 2.扫描文件", required = true)
    @NotNull
    private Short type;

    @ApiModelProperty(value = "顺序", required = true)
    @NotNull
    private Integer order;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "档案库id", required = true)
    @NotNull
    private Integer archivesLibraryId;

    @ApiModelProperty(value = "动态表id", required = true)
    @NotNull
    private Integer dataId;

}
