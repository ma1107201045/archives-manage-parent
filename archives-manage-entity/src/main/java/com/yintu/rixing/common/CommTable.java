package com.yintu.rixing.common;

import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/1/11 18:11:03
 * @Version: 1.0
 */
@Data
public class CommTable {
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
    private String length;

    /**
     * 小数点
     */
    private Integer decimalPoint;
    /**
     * 是否为空
     */
    private Boolean isNull;
    /**
     * 是否主键
     */
    private Boolean isPrimaryKey;

    /**
     * 是否自增
     */
    private Boolean isAutoIncrement;

    /**
     * 是否索引
     */
    private Boolean isIndex;
}
