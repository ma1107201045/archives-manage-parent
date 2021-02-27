package com.yintu.rixing.vo.index;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2021/2/26 11:01:23
 * @Version: 1.0
 */
@Data
@ApiModel
public class InxLocalOrRemoteBorrowApplyVo {

    @ApiModelProperty(value = "档案号", position = 1)
    private String archivesNumber;

    @ApiModelProperty(value = "档案库名称", position = 2)
    private String archivesName;

    @ApiModelProperty(value = "上一环办理人", position = 3)
    private String transactor;

    @ApiModelProperty(value = "借阅人", position = 3)
    private String borrower;

    @ApiModelProperty(value = "借阅日期", position = 4)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date borrowDate;

}
