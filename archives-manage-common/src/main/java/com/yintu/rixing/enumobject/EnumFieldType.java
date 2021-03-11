package com.yintu.rixing.enumobject;

/**
 * @Author: mlf
 * @Date: 2021/3/11 9:30:49
 * @Version: 1.0
 */
public enum EnumFieldType {
    /**
     *
     */
    BASE("基础库的字段", (short) 1, ""),
    /**
     *
     */
    COMMON("公共库的字段", (short) 2, ""),
    /**
     *
     */
    CUSTOM("公共库的字段", (short) 3, "");

    private final String name;

    private final Short value;

    private final String description;

    EnumFieldType(String name, Short value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Short getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
