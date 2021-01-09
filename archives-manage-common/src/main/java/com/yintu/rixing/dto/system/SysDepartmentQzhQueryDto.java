package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.common.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mlf
 * @Date: 2020/12/31 14:30:02
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysDepartmentQzhQueryDto extends PageDto {

    @ApiModelProperty(value = "全宗号名称")
    private String name;

    @ApiModelProperty(value = "部门id")
    private Integer departmentId;
}
