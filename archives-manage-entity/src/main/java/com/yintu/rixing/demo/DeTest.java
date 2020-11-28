package com.yintu.rixing.demo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author mlf
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("demo_test")
@ApiModel(value = "DemoTest对象", description = "")
public class DeTest extends IdEntity {

    private static final long serialVersionUID = 1L;

    @TableField("create_by")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    @TableField("create_date")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @TableField("name")
    @ApiModelProperty(value = "用户名")
    private String name;

    @TableField("age")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @TableField("email")
    @ApiModelProperty(value = "邮箱")
    private String email;


}
