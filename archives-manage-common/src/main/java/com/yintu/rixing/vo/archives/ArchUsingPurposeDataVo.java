package com.yintu.rixing.vo.archives;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/19 14:51:32
 * @Version: 1.0
 */
@Data
@ApiModel
public class ArchUsingPurposeDataVo {

    @ApiModelProperty(value = "利用目的名称", position = 1)
    private String name;

    private List<Long> values;
}
