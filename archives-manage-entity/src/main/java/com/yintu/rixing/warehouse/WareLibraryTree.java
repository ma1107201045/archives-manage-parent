package com.yintu.rixing.warehouse;

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
 * 库房管理的树表
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ware_library_tree")
@ApiModel(value="WareLibraryTree对象", description="库房管理的树表")
public class WareLibraryTree extends BaseEntity {

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
