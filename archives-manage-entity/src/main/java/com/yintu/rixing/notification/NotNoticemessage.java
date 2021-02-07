package com.yintu.rixing.notification;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.vo.make.MakeBorrowAuditorVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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

    @ApiModelProperty(value = "部门id")
    @TableField("department_id")
    private Integer departmentId;

    @ApiModelProperty(value = "部门名")
    @TableField(exist = false)
    private String departmentName;

    @ApiModelProperty(value = "父ids")
    @TableField(exist = false)
    private List<Integer> parentIds;


}
