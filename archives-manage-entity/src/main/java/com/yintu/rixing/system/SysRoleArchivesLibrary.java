package com.yintu.rixing.system;

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
 * 系统角色档案库中间表
 * </p>
 *
 * @author mlf
 * @since 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_archives_library")
@ApiModel(value="SysRoleArchivesLibrary对象", description="系统角色档案库中间表")
public class SysRoleArchivesLibrary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Integer roleId;

    @ApiModelProperty(value = "档案库id")
    @TableField("archives_library_id")
    private Integer archivesLibraryId;


}
