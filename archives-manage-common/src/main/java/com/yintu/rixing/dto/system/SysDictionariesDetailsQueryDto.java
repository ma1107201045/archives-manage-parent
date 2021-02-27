package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2021/2/27 17:25:45
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysDictionariesDetailsQueryDto extends PageDto {

    @ApiModelProperty(value = "数据字典id", hidden = true)
    private Integer dictionariesId;
}
