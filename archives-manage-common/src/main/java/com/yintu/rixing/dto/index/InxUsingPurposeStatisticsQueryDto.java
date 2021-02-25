package com.yintu.rixing.dto.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2021/2/25 18:15:20
 * @Version: 1.0
 */
@Data
@ApiModel
public class InxUsingPurposeStatisticsQueryDto {

    @ApiModelProperty(value = "档案库名称", position = 1)
    private Date date;

    @ApiModelProperty(value = "利用目的id", position = 2)
    private Integer makeId;

}
