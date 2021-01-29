package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mlf
 * @Date: 2021/1/28 17:20:10
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysRemoteUserQueryDto extends PageDto {

    @ApiModelProperty(value = "姓名")
    private String username;

    @ApiModelProperty(value = "查询人身份 1.学生 2.老师 3.员工 4.领导")
    private Short searchIdentityId;

    @ApiModelProperty(value = "证件号码")
    private String certificateNumber;

    @ApiModelProperty(value = "联系电话")
    private String phoneNum;
}
