package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author mlf
 * @since 2020-11-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_department_qzh")
@ApiModel(value = "SysDepartmentQzh对象", description = "系统全宗号表")
public class SysDepartmentQzh extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "全宗号名称", required = true)
    @TableField("name")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "全宗号", required = true)
    @TableField("number")
    @NotBlank
    private String number;

    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "是否是档案馆 1.是 0.否", required = true)
    @TableField("archives_center")
    @NotNull
    private Integer archivesCenter;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "部门id", required = true)
    @TableField("department_id")
    @NotNull
    private Integer departmentId;

}
