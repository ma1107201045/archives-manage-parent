package com.yintu.rixing.dto.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.dto.common.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mlf
 * @Date: 2021/1/11 12:02:26
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysArchivesLibraryFormDto extends IdDto {

    @ApiModelProperty(value = "父节点主键", required = true)
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "档案库名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "key（定义数据库表名）")
    @TableField("data_key")
    private String dataKey;

    @ApiModelProperty(value = "档案库分类 1.目录 2.档案库")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "档案库描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "模板库id")
    @TableField("template_library_id")
    private Integer templateLibraryId;

}
