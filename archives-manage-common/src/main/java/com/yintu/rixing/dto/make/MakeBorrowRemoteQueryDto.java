package com.yintu.rixing.dto.make;

import com.yintu.rixing.dto.base.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mlf
 * @Date: 2021/2/3 17:33:33
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class MakeBorrowRemoteQueryDto extends PageDto {

    @ApiModelProperty(value = "借阅人id", hidden = true)
    private Integer userId;

    @ApiModelProperty(value = "内部和远程人员区分 1：内部 2：远程", hidden = true)
    private Short userType;
}
