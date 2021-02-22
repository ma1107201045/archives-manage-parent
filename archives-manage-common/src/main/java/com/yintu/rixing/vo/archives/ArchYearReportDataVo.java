package com.yintu.rixing.vo.archives;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/22 15:49:30
 * @Version: 1.0
 */
@Data
@ApiModel
public class ArchYearReportDataVo {

    @ApiModelProperty(value = "档案库名称", position = 1)
    private List<String> names;

    @ApiModelProperty(value = "每个维度的每个档案库文件个数", position = 2)
    private List<List<Long>> values;
}
