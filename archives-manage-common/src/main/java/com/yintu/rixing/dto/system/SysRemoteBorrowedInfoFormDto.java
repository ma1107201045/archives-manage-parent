package com.yintu.rixing.dto.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2021/2/1 14:07:08
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysRemoteBorrowedInfoFormDto extends IdDto {

    @ApiModelProperty(value = "借阅内容")
    private String context;

    @ApiModelProperty(value = "借阅目的")
    private String goal;

    @ApiModelProperty(value = "借阅类型 1.电子借阅 2.试题借阅", required = true)
    @NotNull
    private Short type;

    @ApiModelProperty(value = "借阅开始日期", required = true)
    @NotNull
    @Future
    private Date startDate;

    @ApiModelProperty(value = "借阅结束日期", required = true)
    @NotNull
    @Future
    private Date endDate;

    @ApiModelProperty(value = "远程用户id", required = true)
    @NotNull
    private Integer remoteUserId;

    @ApiModelProperty(value = "借阅实物id", required = true)
    @NotNull
    private Integer dataId;

}
