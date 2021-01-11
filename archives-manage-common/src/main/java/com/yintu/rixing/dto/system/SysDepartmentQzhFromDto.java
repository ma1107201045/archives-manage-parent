package com.yintu.rixing.dto.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.dto.common.IdDto;
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

    @ApiModelProperty(value = "全宗号", required = true)
    @NotBlank
    private String number;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "是否是档案馆 1.是 0.否", required = true)
    @NotNull
    private Integer archivesCenter;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "部门id", required = true)
    @NotNull
    private Integer departmentId;

}
