package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mlf
 * @Date: 2020/12/31 14:10:59
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysTemplateLibraryFieldQueryDto extends PageDto {

    @ApiModelProperty(value = "模板库id")
    private Integer templateLibraryId;
}
