package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统档案库表
 * </p>
 *
 * @author mlf
 * @since 2021-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_archives_library")
@ApiModel(value = "SysArchivesLibrary对象", description = "系统档案库表")
public class SysArchivesLibrary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父节点主键")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "档案库名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "key（定义数据库表名）")
    @TableField(value = "data_key", updateStrategy = FieldStrategy.IGNORED)
    private String dataKey;

    @ApiModelProperty(value = "分类 1.目录 2.档案库")
    @TableField("type")
    private Short type;

    @ApiModelProperty(value = "档案库分类 1.案卷级 2.一文一件")
    @TableField("arch_type")
    private Short archType;

    @ApiModelProperty(value = "档案库描述")
    @TableField("description")
    private String description;


}
