package com.yintu.rixing.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: mlf
 * @Date: 2021/1/11 18:11:03
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommTableField {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 长度
     */
    private Integer length;

    /**
     * 小数点
     */
    private Short decimalPoint;
    /**
     * 是否为空
     */
    private Short isNull;

    /**
     * 是否索引
     */
    private Short isIndex;

    /**
     * 注释
     */
    private String comment;

}
