package com.yintu.rixing.vo.archives;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @Author: xsf
 * @Date: 2021/3/15 13:50:43
 * @Version: 1.0
 */
@Data
@ApiModel
public class ArchivesCommonVo {

    @ApiModelProperty(value = "档案库id", position = 1)
    private Integer id;

    @ApiModelProperty(value = "档案库名称", position = 2)
    private String name;

    @ApiModelProperty(value = "分类(1:目录;2:档案库)", position = 3)
    @TableField("type")
    private Short type;

    @ApiModelProperty(value = "档案库类型(1:案卷级;2:一文一件)", position = 4)
    @TableField("arch_type")
    private Short archivesType;

    @ApiModelProperty(value = "当前值", position = 5)
    private Long nowValues;

    @ApiModelProperty(value = "以前的值", position = 6)
    private Long lastValues;

    @ApiModelProperty(value = "类型", position = 7)
    private Short mType;

    @ApiModelProperty(value = "数据", position = 8)
    private Map<String,Object> dataMap;
//    @ApiModelProperty(value = "创建日期(yyyy-MM-dd)", position = 7)
//    private Date createDate;
}
