package com.yintu.rixing.noticemessage;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.yintu.rixing.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 通知公告表
 * </p>
 *
 * @author Mr.liu
 * @since 2020-12-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("not_noticemessage")
@ApiModel(value="NotNoticemessage对象", description="通知公告表")
public class NotNoticemessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "内容")
    @TableField("context")
    private String context;

    @ApiModelProperty(value = "部门")
    @TableField("department")
    private String department;


}
