package com.yintu.rixing.dto.make;

import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2021/2/3 17:10:29
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class MakeBorrowRemoteFormDto extends IdDto {
    @ApiModelProperty(value = "文件id")
    @NotNull
    private Integer fileId;

    @ApiModelProperty(value = "借阅人id", hidden = true)
    private Integer userId;

    @ApiModelProperty(value = "借阅开始时间")
    @NotNull
    private Date borrowStartTime;

    @ApiModelProperty(value = "借阅结束时间")
    @NotNull
    private Date borrowEndTime;

    @ApiModelProperty(value = "利用目的id")
    @NotNull
    private Date borrowPurposeId;

    @ApiModelProperty(value = "内部和远程人员区分 1：内部 2：远程", hidden = true)
    private Short userType;

    @ApiModelProperty(value = "借阅类型 1：电子借阅 2：实体借阅")
    @NotNull
    private Short borrowType;

    @ApiModelProperty(value = "备注")
    private String remarks;

//    @ApiModelProperty(value = "审核人id集")
//    @NotNull
//    @Size(min = 1)
//    private List<Integer> auditorIds;
//
//    @ApiModelProperty(value = "排序集")
//    @NotNull
//    @Size(min = 1)
//    private List<Integer> sortIds;

}
