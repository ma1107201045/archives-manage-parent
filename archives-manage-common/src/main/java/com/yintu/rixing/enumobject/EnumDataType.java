package com.yintu.rixing.enumobject;

/**
 * @Author: mlf
 * @Date: 2021/1/18 19:20:01
 * @Version: 1.0
 */
public enum EnumDataType {

    VARCHAR("文本框(文本)", 1, "varchar", ""),
    TEXT("文本域", 2, "text", ""),
    TINYINT("单选框", 3, "tinyint", ""),
    SMALLINT("文本框(状态值)", 4, "smallint", ""),
    INT("文本框(数值)", 5, "int", ""),
    DATETIME("日期时间框", 6, "datetime", ""),
    DATE("日期框", 7, "date", "");


    private final String name;
    private final Integer value;
    private final String dataKey;
    private final String description;

    EnumDataType(String name, Integer value, String dataKey, String description) {
        this.name = name;
        this.value = value;
        this.dataKey = dataKey;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public String getDataKey() {
        return dataKey;
    }

    public String getDescription() {
        return description;
    }

    public static EnumDataType get(Integer value) {
        EnumDataType[] enumDataTypes;
        int length = (enumDataTypes = values()).length;
        for (int i = 0; i < length; ++i) {
            EnumDataType enumDataType = enumDataTypes[i];
            if (value.equals(enumDataType.getValue())) {
                return enumDataType;
            }
        }
        return get(1);
    }
}
