package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统档案库字段表（档案设置表）
 * </p>
 *
 * @author mlf
 * @since 2021-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_archives_library_number_setting")
@ApiModel(value="SysArchivesLibraryNumberSetting对象", description="系统档案库字段表（档案设置表）")
public class SysArchivesLibraryNumberSetting extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "档案库id")
    @TableField("archives_library_id")
    private Integer archivesLibraryId;

    @ApiModelProperty(value = "分隔符 1.- 2.·3..4.~5.=6./7.`")
    @TableField("separator")
    private String separator;

    @ApiModelProperty(value = "档案库字段id")
    @TableField("archives_library_field_id")
    private Integer archivesLibraryFieldId;

    @ApiModelProperty(value = "自定义字符")
    @TableField("custom_character")
    private String customCharacter;


}
