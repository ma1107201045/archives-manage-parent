package com.yintu.rixing.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/1/19 16:36:06
 * @Version: 1.0
 */
@Data
@ApiModel
public class CommFileVo {

    @ApiModelProperty(value = "文件名", position = 1)
    private String fileName;
    @ApiModelProperty(value = "文件路径", position = 2)
    private String filePath;
    @ApiModelProperty(value = "文件大小", position = 3)
    private String fileSize;
    @ApiModelProperty(value = "请求映射", position = 4)
    private String requestMapping;

}
