package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.common.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mlf
 * @Date: 2020/12/29 13:31:47
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysRoleQueryDto extends PageDto {
    @ApiModelProperty(value = "角色名")
    private String name;
}
