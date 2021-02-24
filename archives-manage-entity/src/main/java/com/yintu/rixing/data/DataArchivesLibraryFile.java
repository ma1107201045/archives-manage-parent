package com.yintu.rixing.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 数据档案库文件表
 * </p>
 *
 * @author mlf
 * @since 2021-01-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("data_archives_library_file")
@ApiModel(value = "DataArchivesLibraryFile对象", description = "数据档案库文件表")
public class DataArchivesLibraryFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "原始文件名", position = 6)
    @TableField("original_name")
    private String originalName;

    @ApiModelProperty(value = "文件路径", position = 7)
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "文件大小", position = 8)
    @TableField("size")
    private Double size;

    @ApiModelProperty(value = "文件大小单位（B、KB、MB、GB）", position = 9)
    @TableField("unit")
    private String unit;

    @ApiModelProperty(value = "文件名", position = 10)
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "请求映射", position = 11)
    @TableField("request_mapping")
    private String requestMapping;

    @ApiModelProperty(value = "文件类型 1.电子文件 2.扫描文件", position = 12)
    @TableField("type")
    private Short type;

    @ApiModelProperty(value = "文件内容", position = 13)
    @TableField("context")
    private String context;

    @ApiModelProperty(value = "文件页数", position = 14)
    @TableField("context")
    private Integer page;

    @ApiModelProperty(value = "顺序", position = 15)
    @TableField("`order`")
    private Integer order;

    @ApiModelProperty(value = "备注", position = 16)
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "档案库id", position = 17)
    @TableField("archives_library_id")
    private Integer archivesLibraryId;

    @ApiModelProperty(value = "动态表id", position = 18)
    @TableField("data_id")
    private Integer dataId;

    @ApiModelProperty(value = "是否正式库", position = 19)
    @TableField("formal_library")
    private Short formalLibrary;

}
