package com.yintu.rixing.enumobject;

/**
 * 保管期限
 *
 * @Author: mlf
 * @Date: 2021/1/27 10:24:26
 * @Version: 1.0
 */
public enum EnumRetentionPeriod {

    PERMANENT("永久", (short) 1, ""),
    LONG_TIME("长期", (short) 2, ""),
    SHORT_TIME("短期", (short) 3, ""),
    TEN_YEARS("十年", (short) 4, ""),
    THIRTY_YEARS("三十年", (short) 5, ""),
    SIXTY_YEARS("六十年", (short) 6, "");

    private String name;
    private Short value;
    private String description;

    EnumRetentionPeriod(String name, Short value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
