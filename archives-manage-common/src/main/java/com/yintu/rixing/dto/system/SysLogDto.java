package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.common.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2020/11/26 17:06
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysLogDto extends PageDto {

    @ApiModelProperty(value = "操作人", position = 0)
    private String operator;

    @ApiModelProperty(value = "登录ip", position = 1)
    private String loginId;

    @ApiModelProperty(value = "日志级别")
    private Short level;

    @ApiModelProperty(value = "开始日期")
    private Date beginDate;

    @ApiModelProperty(value = "结束日期")
    private Date endDate;

}
