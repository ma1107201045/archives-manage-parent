package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.common.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2020/12/30 16:08:44
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysTemplateLibraryFormDto extends IdDto {

    @ApiModelProperty(value = "父节点主键", required = true)
    @NotNull
    private Integer parentId;

    @ApiModelProperty(value = "模板库编号")
    private Integer number;

    @ApiModelProperty(value = "模板库名称")
    @NotNull
    private String name;

    @ApiModelProperty(value = "模板库分类 1.目录 2.库")
    @NotNull
    private Short type;

    @ApiModelProperty(value = "模板库描述")
    private String description;


}
