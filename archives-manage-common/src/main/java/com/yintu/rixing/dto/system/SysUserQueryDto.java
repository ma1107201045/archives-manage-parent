package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.common.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mlf
 * @Date: 2020/11/26 13:54
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserQueryDto extends PageDto {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户名称")
    private String nickname;

}
