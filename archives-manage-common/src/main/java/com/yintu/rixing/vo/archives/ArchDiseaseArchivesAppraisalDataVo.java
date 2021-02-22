package com.yintu.rixing.vo.archives;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/22 11:31:30
 * @Version: 1.0
 */
@Data
@ApiModel
public class ArchDiseaseArchivesAppraisalDataVo {
    @ApiModelProperty(value = "档案库名称", position = 1)
    private List<String> names;

    @ApiModelProperty(value = "审核状态中的每个档案库借阅次数值", position = 2)
    private List<List<Long>> values;
}
