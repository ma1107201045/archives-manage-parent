package com.yintu.rixing.make;

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
 * 档案收藏表
 * </p>
 *
 * @author Mr.liu
 * @since 2021-03-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("make_archives_collection")
@ApiModel(value="MakeArchivesCollection对象", description="档案收藏表")
public class MakeArchivesCollection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录人id")
    @TableField("userid")
    private Integer userid;

    @ApiModelProperty(value = "档案库id")
    @TableField("archivesLibid")
    private Integer archiveslibid;

    @ApiModelProperty(value = "档案id")
    @TableField("archivesid")
    private Integer archivesid;


}
