package com.yintu.rixing.dto.archives;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/30 14:24:38
 * @Version: 1.0
 */
@Data
@ApiModel
public class ArchCommonQueryDto {

    @ApiModelProperty(value = "部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "年份")
    private Integer year;

    @ApiModelProperty(value = "开始日期")
    private Date startDate;

    @ApiModelProperty(value = "结束日期")
    private Date endDate;

    @ApiModelProperty(value = "档案库id集合")
    @Size(min = 1, message = "请选择一个档案库")
    @NotNull
    private List<Integer> archivesIds;


}
