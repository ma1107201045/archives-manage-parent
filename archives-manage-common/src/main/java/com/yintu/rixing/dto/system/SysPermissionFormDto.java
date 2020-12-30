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
public class SysPermissionFormDto extends FormDto {

    @ApiModelProperty(value = "父节点主键", required = true)
    @NotNull
    private Integer parentId;

    @ApiModelProperty(value = "权限名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "授权名称")
    private String authorizedName;

    @ApiModelProperty(value = "是否是菜单项 1.是 0.否", required = true)
    @NotNull
    private Short menu;

    @ApiModelProperty(value = "相对地址")
    private String url;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "前端路由转向")
    private String path;

    @ApiModelProperty(value = "菜单项显示的图片地址")
    private String imgPath;


}
