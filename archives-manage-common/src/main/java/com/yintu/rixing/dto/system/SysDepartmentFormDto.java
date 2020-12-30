package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.common.FormDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2020/12/29 13:31:47
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDepartmentFormDto extends FormDto {

    @ApiModelProperty(value = "父节点主键", required = true)
    @NotNull
    private Integer parentId;

    @ApiModelProperty(value = "权限名称", required = true)
    @NotBlank
    private String name;
}
