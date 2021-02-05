package com.yintu.rixing.dto.person;

import com.yintu.rixing.dto.base.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Author: mlf
 * @Date: 2021/2/5 16:14:41
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class PerBorrowManagementQueryDto extends PageDto {

    @ApiModelProperty(value = "类型 1.待处理 2.已处理 3.我借阅的")
    @NotNull
    private Short type;

    @ApiModelProperty(value = "内部和远程人员区分 1：内部 2：远程", hidden = true)
    private Short userType;

    @ApiModelProperty(value = "借阅人id", hidden = true)
    private Integer userId;

    @ApiModelProperty(value = "借阅类型 1：电子借阅 2：实体借阅")
    private Short borrowType;

    @ApiModelProperty(value = "档案目录编号")
    private String archivesDirectoryNum;
}
