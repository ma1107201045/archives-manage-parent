package com.yintu.rixing.vo.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/26 11:53:09
 * @Version: 1.0
 */
@Data
@ApiModel
public class InxLocalAndRemoteBorrowApplyAndInformVo {


    @ApiModelProperty(value = "本地借阅申请", position = 1)
    List<InxLocalOrRemoteBorrowApplyVo> localBorrowApplies;

    @ApiModelProperty(value = "远程借阅申请", position = 2)
    List<InxLocalOrRemoteBorrowApplyVo> remoteBorrowApplies;

    @ApiModelProperty(value = "通知公告", position = 3)
    List<InxInformVo> informs;
}
