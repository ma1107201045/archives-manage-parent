package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.PageDto;
import io.swagger.annotations.ApiModel;
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
@ApiModel
public class SysUserQueryDto extends PageDto {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户名称")
    private String nickname;

    @ApiModelProperty(value = "部门主键id")
    private Integer departmentId;

}
