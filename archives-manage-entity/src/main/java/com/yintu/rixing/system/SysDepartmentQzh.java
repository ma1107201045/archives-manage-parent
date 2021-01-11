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
    private String name;

    @ApiModelProperty(value = "全宗号")
    @TableField("number")
    private String number;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "部门id", required = true)
    @TableField("department_id")
    @NotNull
    private Integer departmentId;

}
