package com.yintu.rixing.dto.system;

import com.yintu.rixing.dto.base.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: mlf
 * @Date: 2021/1/14 10:53:13
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SysArchivesLibraryFieldQueryDto extends PageDto {


    @ApiModelProperty(value = "档案库id")
    private Integer archivesLibraryId;
}
