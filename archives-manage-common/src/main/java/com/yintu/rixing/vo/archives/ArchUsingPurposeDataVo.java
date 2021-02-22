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
public class ArchUsingPurposeDataVo {

    private String name;

    private List<Long> values;
}
