package com.yintu.rixing.util;

/**
 * @Author: mlf
 * @Date: 2021/1/12 13:55:40
 * @Version: 1.0
 */
public class TableNameUtil {

    public static final String PREFIX = "dynamic";

    /**
     * @param tName 表名
     * @return 获取表全名
     */
    public static String getFullTableName(String tName) {
        return PREFIX + "_" + tName;
    }
}
