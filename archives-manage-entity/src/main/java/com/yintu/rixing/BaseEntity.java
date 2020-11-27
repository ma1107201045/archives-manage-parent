package com.yintu.rixing;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2020/11/25 13:32
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity extends IdEntity {

    private static final long serialVersionUID = 7607862306834821931L;

    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新人")
    private String modifiedBy;
    @ApiModelProperty(value = "更新时间")
    private Date modifiedTime;
}
