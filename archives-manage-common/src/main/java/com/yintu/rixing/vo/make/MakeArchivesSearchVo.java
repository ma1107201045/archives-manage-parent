package com.yintu.rixing.vo.make;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:08:09
 * @Version: 1.0
 */
@Data
@ApiModel
public class MakeArchivesSearchVo {

    @ApiModelProperty(value = "档案库id", position = 1)
    private Integer archivesLibId;

    @ApiModelProperty(value = "档案库名称", position = 2)
    private String archivesLibName;

    @ApiModelProperty(value = "档案库类型 1.电子档案库 2.实体档案库", position = 3)
    private Short archivesLibType;

    @ApiModelProperty(value = "档案目录id", position = 4)
    private Integer archivesDirectoryId;

    @ApiModelProperty(value = "档案目录编号", position = 5)
    private String archivesDirectoryNum;

    @ApiModelProperty(value = "档案文件id", position = 6)
    private Integer archivesFileId;

    @ApiModelProperty(value = "档案文件名", position = 7)
    private String archivesFileOriginalName;

    @ApiModelProperty(value = "档案文件内容", position = 8)
    private String archivesFileContext;
}
