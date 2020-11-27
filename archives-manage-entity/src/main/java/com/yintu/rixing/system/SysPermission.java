package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统权限表
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
@ApiModel(value = "SysPermission对象", description = "系统权限表")
public class SysPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父节点主键")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "权限名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "优先级")
    @TableField("priority")
    private Integer priority;

    @ApiModelProperty(value = "授权名称")
    @TableField("authorized_name")
    private String authorizedName;

    @ApiModelProperty(value = "相对地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "请求方法")
    @TableField("method")
    private String method;

    @ApiModelProperty(value = "是否是菜单项 1.是 0.否")
    @TableField("menu")
    private Short menu;

    @ApiModelProperty(value = "前端路由转向")
    @TableField("img_path")
    private String imgPath;

    @ApiModelProperty(value = "菜单项显示的图片地址")
    @TableField("icon_cls")
    private String iconCls;

    @ApiModelProperty(value = "模块状态：1.可用 0. 禁用")
    @TableField("status")
    private Short status;


}
