package com.yintu.rixing.util;

/**
 * @Author: mlf
 * @Date: 2021/1/12 13:55:40
 * @Version: 1.0
 */
public class TableNameUtil {

    public static final String PREFIX = "data";

    public static final String ROLLBACK_SUFFIX = "_rollback_info";
    public static final String ROLLBACK_COMMENT_SUFFIX = "-回退管理记录表";

    public static final String tableName = "ware_physical_warehouse";


    /**
     * @param tName 表名
     * @return 获取表全名
     */
    public static String getFullTableName(String tName) {
        AssertUtil.notEmpty(tName, "key不能为空");
        return PREFIX + "_" + tName;
    }

    /**
     * @param tName 表名
     * @return 获取表全回退名
     */
    public static String getRollbackTableName(String tName) {
        AssertUtil.notEmpty(tName, "key不能为空");
        return getFullTableName(tName) + ROLLBACK_SUFFIX;
    }


}
