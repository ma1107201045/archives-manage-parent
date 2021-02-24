package com.yintu.rixing.dto.make;

import com.yintu.rixing.dto.base.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author: mlf
 * @Date: 2021/2/2 17:26:31
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class MakeArchivesSearchDto extends PageDto {

    @ApiModelProperty(value = "关键字（至少输入两个字）", required = true)
    @NotBlank
    @Length(min = 2, message = "搜索的关键字长度不能小于长度2")
    private String keyWord;

    @ApiModelProperty(value = "用户类型", hidden = true)
    private Short userType;

    @ApiModelProperty(value = "用户id", hidden = true)
    private Integer userId;
}
