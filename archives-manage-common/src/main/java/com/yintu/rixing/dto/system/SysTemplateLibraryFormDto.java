package com.yintu.rixing.dto.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.dto.common.FormDto;
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
public class SysTemplateLibraryFormDto extends FormDto {

    @ApiModelProperty(value = "父节点主键", required = true)
    @NotNull
    private Integer parentId;

    @ApiModelProperty(value = "模板库编号")
    @NotNull
    private Integer number;

    @ApiModelProperty(value = "模板库名称")
    @NotNull
    private String name;

    @ApiModelProperty(value = "模板库分类 1.目录 2.库")
    @NotNull
    private Integer type;

    @ApiModelProperty(value = "模板库描述")
    private String description;


}
