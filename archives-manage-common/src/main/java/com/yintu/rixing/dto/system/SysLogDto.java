package com.yintu.rixing.dto.system;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

;

/**
 * @Author: mlf
 * @Date: 2020/11/26 17:06
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysLogDto对象", description = "系统日志Dto")
public class SysLogDto {

    @ApiModelProperty(name = "operator", value = "登录名称", dataType = "string")
    private String operator;

    @ApiModelProperty(name = "loginId", value = "登录ip", dataType = "string")
    private String loginId;

    @ApiModelProperty(name = "level", value = "日志级别", dataType = "string")
    private String level;

    @ApiModelProperty(name = "beginTime", value = "开始日期", dataType = "date")
    private String beginTime;

    @ApiModelProperty(name = "endTime", value = "结束日期", dataType = "date")
    private String endTime;

}
