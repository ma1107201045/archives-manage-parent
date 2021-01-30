package com.yintu.rixing.vo.archives;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/1/30 14:23:43
 * @Version: 1.0
 */
@Data
@ApiModel
public class ArchQuantityStatisticsQueryVo {

    @ApiModelProperty(value = "档案库id", position = 1)
    private Integer archivesId;

    @ApiModelProperty(value = "档案库名称", position = 2)
    private String archivesName;
}
