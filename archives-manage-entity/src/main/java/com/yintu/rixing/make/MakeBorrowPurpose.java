package com.yintu.rixing.make;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 利用目的表
 * </p>
 *
 * @author mlf
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("make_borrow_purpose")
@ApiModel(value="MakeBorrowPurpose对象", description="利用目的表")
public class MakeBorrowPurpose extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "利用目的名")
    @TableField("name")
    private String name;


}
