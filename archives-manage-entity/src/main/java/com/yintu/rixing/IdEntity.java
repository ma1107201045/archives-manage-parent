package com.yintu.rixing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: mlf
 * @Date: 2020/11/25 13:35
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode
public class IdEntity implements Serializable {
    private static final long serialVersionUID = -2411492813615673916L;

    @ApiModelProperty(value = "主键id", hidden = true)
    @TableId(type = IdType.AUTO)
    private Integer id;
}
