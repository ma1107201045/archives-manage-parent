package com.yintu.rixing.dto.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yintu.rixing.dto.base.IdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private Integer parentId;

    @ApiModelProperty(value = "档案库编号")
    @TableField("number")
    private Integer number;

    @ApiModelProperty(value = "档案库名称", required = true)
    @TableField("name")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "key（定义数据库表名）")
    @TableField("data_key")
    private String dataKey;

    @ApiModelProperty(value = "档案库分类 1.目录 2.档案库", required = true)
    @TableField("type")
    @NotNull
    private Short type;

    @ApiModelProperty(value = "档案库描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "模板库id", required = true)
    @TableField("template_library_id")
    private Integer templateLibraryId;

}
