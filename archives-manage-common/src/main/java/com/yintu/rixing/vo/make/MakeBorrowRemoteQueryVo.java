package com.yintu.rixing.vo.make;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2021/2/3 17:57:20
 * @Version: 1.0
 */
@Data
@ApiModel
public class MakeBorrowRemoteQueryVo {
    @ApiModelProperty(value = "档案库名称", position = 1)
    private String archivesLibName;

    @ApiModelProperty(value = "档案目录编号", position = 2)
    private String archivesDirectoryNum;

    @ApiModelProperty(value = "档案目录题名", position = 3)
    private String archivesDirectoryTopicName;

    @ApiModelProperty(value = "档案目录保管期限", position = 4)
    private Object archivesDirectoryRetentionPeriod;

    @ApiModelProperty(value = "档案目录有效期", position = 5)
    private Object archivesDirectoryValidPeriod;

    @ApiModelProperty(value = "档案目录密级", position = 6)
    private Object archivesDirectorySecurityLevel;

    @ApiModelProperty(value = "档案目录归档年份", position = 7)
    private String archivesDirectoryFilingAnnual;

    @ApiModelProperty(value = "档案文件名", position = 8)
    private String archivesFileOriginalName;

    @ApiModelProperty(value = "借阅人姓名", position = 8)
    private Integer userName;

    @ApiModelProperty(value = "证件号码", position = 9)
    private Integer certificateNumber;

    @ApiModelProperty(value = "借阅开始时间", position = 10)
    private Date borrowStartTime;

    @ApiModelProperty(value = "借阅结束时间", position = 11)
    private Date borrowEndTime;

    @ApiModelProperty(value = "利用目的名称", position = 12)
    private Date borrowPurposeName;

    @ApiModelProperty(value = "借阅类型 1：电子借阅 2：实体借阅", position = 13)
    private Short borrowType;

    @ApiModelProperty(value = "备注", position = 14)
    private String remarks;

    @ApiModelProperty(value = "审核状态 1.正常 2.审核中 3.通过 4.拒绝", position = 15)
    private Short auditStatus;

    @ApiModelProperty(value = "审核完成时间", position = 16)
    private Date auditFinishTime;

    @ApiModelProperty(value = "预览类型 0：不可预览  1：可预览", position = 17)
    private Short previewType;
}
