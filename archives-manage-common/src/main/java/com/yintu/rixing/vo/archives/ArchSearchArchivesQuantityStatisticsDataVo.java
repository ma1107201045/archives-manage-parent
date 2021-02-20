package com.yintu.rixing.vo.archives;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/20 15:11:08
 * @Version: 1.0
 */
@Data
@ApiModel
public class ArchSearchArchivesQuantityStatisticsDataVo {


    @ApiModelProperty(value = "档案库名称", position = 1)
    private List<String> names;

    @ApiModelProperty(value = "每个档案库文件查询次数值", position = 2)
    private List<Long> values;
}
