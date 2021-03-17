package com.yintu.rixing.enumobject;

/**
 * @Author: mlf
 * @Date: 2021/1/18 19:20:01
 * @Version: 1.0
 */
public enum EnumDataType {

    VARCHAR("文本框(文本)", 1, "varchar", ""),
    INT("文本框(数值)", 2, "int", ""),
    SMALLINT("文本框(状态数值)", 3, "smallint", ""),
    TEXT("文本域", 4, "text", ""),
    DATE("日期框", 5, "date", ""),
    DATETIME("日期时间框", 6, "datetime", ""),
    TINYINT("单选框", 7, "tinyint", ""),
    DROPDOWNLIST("下拉框", 8, "varchar", "");


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
