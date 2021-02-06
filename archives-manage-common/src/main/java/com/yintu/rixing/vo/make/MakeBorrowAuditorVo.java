package com.yintu.rixing.vo.make;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2021/2/6 19:50:48
 * @Version: 1.0
 */
@Data
@ApiModel
public class MakeBorrowAuditorVo {

    @ApiModelProperty(value = "借阅记录id", position = 1)
    private Integer makeBorrowId;

    @ApiModelProperty(value = "审核人id", position = 2)
    private Integer auditorId;

    @ApiModelProperty(value = "审核人名", position = 3)
    private String username;

    @ApiModelProperty(value = "审核人名称", position = 4)
    private String nickname;

    @ApiModelProperty(value = "排序", position = 5)
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "当前人是否在审核中 1.是 0.否", position = 6)
    private Short activate;

    @ApiModelProperty(value = "审核内容", position = 7)
    private String context;

    @ApiModelProperty(value = "附件名", position = 8)
    private String accessoryName;

    @ApiModelProperty(value = "附件路径", position = 9)
    private String accessoryPath;

    @ApiModelProperty(value = "是否处理1.已处理 0.待处理", position = 10)
    private Short isDispose;

    @ApiModelProperty(value = "1.审核中 2.通过 3.拒绝 4.转交", position = 11)
    private Short auditStatus;

    @ApiModelProperty(value = "审核完成时间", position = 12)
    private Date auditFinishTime;


}
