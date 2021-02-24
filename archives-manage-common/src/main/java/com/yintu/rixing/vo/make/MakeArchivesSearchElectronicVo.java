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
public class MakeArchivesSearchElectronicVo {

    @ApiModelProperty(value = "档案库id", position = 1)
    private Integer archivesLibId;

    @ApiModelProperty(value = "档案库名称", position = 2)
    private String archivesLibName;

    @ApiModelProperty(value = "档案目录id", position = 4)
    private Integer archivesDirectoryId;

    @ApiModelProperty(value = "档案目录编号", position = 5)
    private String archivesDirectoryNum;

    @ApiModelProperty(value = "档案目录题名", position = 6)
    private String archivesDirectoryTopicName;

    @ApiModelProperty(value = "档案目录保管期限", position = 7)
    private Object archivesDirectoryRetentionPeriod;

    @ApiModelProperty(value = "档案目录有效期", position = 8)
    private Object archivesDirectoryValidPeriod;

    @ApiModelProperty(value = "档案目录密级", position = 9)
    private Object archivesDirectorySecurityLevel;

    @ApiModelProperty(value = "档案目录归档年份", position = 10)
    private String archivesDirectoryFilingAnnual;

    @ApiModelProperty(value = "档案文件id", position = 11)
    private Integer archivesFileId;

    @ApiModelProperty(value = "档案文件名", position = 12)
    private String archivesFileOriginalName;

    @ApiModelProperty(value = "档案文件内容", position = 13)
    private String archivesFileContext;

    @ApiModelProperty(value = "档案文件路径", position = 14)
    private String archivesFileRequestMapping;
}
