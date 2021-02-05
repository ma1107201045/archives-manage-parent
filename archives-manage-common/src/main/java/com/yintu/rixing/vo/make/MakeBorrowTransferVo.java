package com.yintu.rixing.vo.make;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/2/5 13:41:18
 * @Version: 1.0
 */
@Data
@ApiModel
public class MakeBorrowTransferVo {
    @ApiModelProperty(value = "审核人id", position = 1)
    private Integer id;

    @ApiModelProperty(value = "审核人用户名", position = 2)
    private String username;

    @ApiModelProperty(value = "审核人名称", position = 3)
    private String nickName;
}
