package com.yintu.rixing.vo.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/25 17:57:20
 * @Version: 1.0
 */
@Data
@ApiModel
public class InxSearchArchivesQuantityStatisticsVo {

    @ApiModelProperty(value = "档案库名称", position = 1)
    private List<String> names;

    @ApiModelProperty(value = "人次 卷次 件此每个档案库值", position = 2)
    private List<List<Long>> values;

}
