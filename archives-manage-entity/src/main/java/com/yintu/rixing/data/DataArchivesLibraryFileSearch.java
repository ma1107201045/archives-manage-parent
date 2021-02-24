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
 * 数据档案库文件搜索记录表
 * </p>
 *
 * @author mlf
 * @since 2021-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("data_archives_library_file_search")
@ApiModel(value = "DataArchivesLibraryFileSearch对象", description = "数据档案库文件搜索记录表")
public class DataArchivesLibraryFileSearch extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "档案库文件id", position = 6)
    @TableField("archives_library_file_id")
    private Integer archivesLibraryFileId;

    @ApiModelProperty(value = "用户类型 1：内部 2：远程", position = 7)
    @TableField("user_type")
    private Short userType;

    @ApiModelProperty(value = "用户id", position = 8)
    @TableField("userId")
    private Integer userId;


}
