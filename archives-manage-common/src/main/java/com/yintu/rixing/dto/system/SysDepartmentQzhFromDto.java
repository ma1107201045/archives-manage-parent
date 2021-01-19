package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2020/12/31 14:29:54
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysDepartmentQzhFromDto extends IdDto {

    @ApiModelProperty(value = "全宗号名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "部门id", required = true)
    @NotNull
    private Integer departmentId;

}
