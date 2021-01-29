package com.yintu.rixing.vo.remote;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/29 9:39:49
 * @Version: 1.0
 */
@Data
@ApiModel
public class RemoAuthenticationVo {

    @ApiModelProperty(value = "远程用户", position = 1)
    private Map<String, Object> remoteUser;

    @ApiModelProperty(value = "token", position = 2)
    private String token;
}
