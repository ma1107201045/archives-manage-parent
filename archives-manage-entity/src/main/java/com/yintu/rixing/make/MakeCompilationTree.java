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
 * 档案编研的树表
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("make_compilation_tree")
@ApiModel(value="MakeCompilationTree对象", description="档案编研的树表")
public class MakeCompilationTree extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父节点主键")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "目录名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "目录分类 1.目录 ")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "目录描述")
    @TableField("description")
    private String description;


}
