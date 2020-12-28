package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mlf
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_borrowaudit")
@ApiModel(value="SysBorrowaudit对象", description="")
public class SysBorrowaudit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "借阅人")
    @TableField("borrower")
    private String borrower;

    @ApiModelProperty(value = "申请日期")
    @TableField("applicationdate")
    private Date applicationdate;

    @ApiModelProperty(value = "归还日期")
    @TableField("returndate")
    private Date returndate;

    @ApiModelProperty(value = "档案号")
    @TableField("recordnumber")
    private String recordnumber;

    @ApiModelProperty(value = "摘要")
    @TableField("abstrac")
    private String abstrac;

    @ApiModelProperty(value = "目的")
    @TableField("purpose")
    private String purpose;

    @ApiModelProperty(value = "借阅方式")
    @TableField("borroweway")
    private String borroweway;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private String status;


}
