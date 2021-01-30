package com.yintu.rixing.dto.archives;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/30 14:24:38
 * @Version: 1.0
 */
@Data
@ApiModel
public class ArchQuantityStatisticsQueryDto {

    @ApiModelProperty(value = "开始日期")
    private Date startDate;

    @ApiModelProperty(value = "结束日期")
    private Date endDate;

    @ApiModelProperty(value = "档案库id集合")
    private List<Integer> archivesIds;


}
