package com.yintu.rixing.vo.index;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2021/2/26 11:54:35
 * @Version: 1.0
 */

@Data
@ApiModel
public class InxInformVo {

    @ApiModelProperty(value = "公告内容", position = 1)
    private String context;

    @ApiModelProperty(value = "发布日期", position = 2)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

}
