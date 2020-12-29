package com.yintu.rixing.dto.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2020/12/29 13:31:47
 * @Version: 1.0
 */
@Data
public class SysRoleFormDto {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "是否作为默认用户角色 1.是 0.否", required = true)
    @NotNull
    private Short defaultRole;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "权限id集", required = true)
    @NotNull
    private Set<Integer> permissionIds;
}
