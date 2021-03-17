package com.yintu.rixing.vo.make;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:08:09
 * @Version: 1.0
 * 档案搜索表头字段
 */
@Data
@ApiModel
public class MakeArchivesSearchVo {

    @ApiModelProperty(value = "档案库id", position = 1)
    private Integer archivesLibId;

    @ApiModelProperty(value = "档案id", position = 2)
    private Integer archivesDirectoryId;

    @ApiModelProperty(value = "档号", position = 3)
    private String archivesCode;

    @ApiModelProperty(value = "id", position = 1)
    private Integer id;

    @ApiModelProperty(value = "案卷号", position = 5)
    private String folderCode;

    @ApiModelProperty(value = "件号", position = 6)
    private String fileCode;

    @ApiModelProperty(value = "题名", position = 7)
    private String title;

    @ApiModelProperty(value = "年度", position = 8)
    private String year;

    @ApiModelProperty(value = "责任者", position = 9)
    private String responsiblePerson;

    @ApiModelProperty(value = "档案类目", position = 11)
    private String archivesFile;


}
