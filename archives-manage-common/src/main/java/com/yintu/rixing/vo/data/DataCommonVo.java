package com.yintu.rixing.vo.data;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/19 11:10:32
 * @Version: 1.0
 */
@Data
@ApiModel
public class DataCommonVo {
    @ApiModelProperty(value = "表头", position = 1)
    private List<DataCommonTitleVo> titles;

    @ApiModelProperty(value = "分页信息", position = 2)
    private Page<Map<String, Object>> page;

}
