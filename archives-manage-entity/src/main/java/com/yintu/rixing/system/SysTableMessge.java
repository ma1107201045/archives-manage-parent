package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
public class SysTableMessge implements Serializable {

    @ApiModelProperty(value = "中文名称")
    @TableField("comment")
    private String comment;

    @ApiModelProperty(value = "字段")
    @TableField("field")
    private String field;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private String type;

    private String Null;

    private String key;

    private String Default;

    private String Extra;



}
