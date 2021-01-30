package com.yintu.rixing.vo.archives;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/30 14:24:08
 * @Version: 1.0
 */
@Data
@ApiModel
public class ArchQuantityStatisticsDataVo {
    @ApiModelProperty(value = "档案库名称", position = 1)
    private List<String> names;
    @ApiModelProperty(value = "每个流程中的每个档案库值", position = 2)
    private List<List<Long>> values;
}
