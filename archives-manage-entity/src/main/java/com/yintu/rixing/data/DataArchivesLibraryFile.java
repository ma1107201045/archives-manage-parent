package com.yintu.rixing.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @ApiModelProperty(value = "文件名", position = 6)
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "文件路径", position = 7)
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "文件大小（MB）", position = 8)
    @TableField("size")
    private Double size;

    @ApiModelProperty(value = "请求映射", position = 9)
    @TableField("request_mapping")
    private String requestMapping;

    @ApiModelProperty(value = "文件类型 1.电子文件 2.扫描文件", position = 10)
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "备注", position = 11)
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "档案库id", position = 12)
    @TableField("archives_library_id")
    private Integer archivesLibraryId;

    @ApiModelProperty(value = "动态表id", position = 13)
    @TableField("data_id")
    private Integer dataId;


}
