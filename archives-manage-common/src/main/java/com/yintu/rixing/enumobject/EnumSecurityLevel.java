package com.yintu.rixing.enumobject;

/**
 * 密级
 *
 * @Author: mlf
 * @Date: 2021/1/27 14:26:36
 * @Version: 1.0
 */
public enum EnumSecurityLevel {

    TOP_SECRET("绝密", (short) 1, ""),
    INTERIOR("内部", (short) 2, ""),
    OPEN("公开", (short) 3, "");

    private final String name;
    private final Short value;
    private final String description;

    EnumSecurityLevel(String name, Short value, String description) {
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
