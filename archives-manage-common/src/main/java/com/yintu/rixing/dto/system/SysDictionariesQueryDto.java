package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mlf
 * @Date: 2021/2/27 16:54:09
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysDictionariesQueryDto extends PageDto {
    @ApiModelProperty(value = "字典名称")
    private String name;
}
