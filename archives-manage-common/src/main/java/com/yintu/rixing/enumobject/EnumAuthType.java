package com.yintu.rixing.enumobject;

/**
 * @author:mlf
 * @date:2020/5/20 13:54
 */
public enum EnumAuthType {

    USER("普通用户", (short) 0, true), ADMIN("管理员", (short) 1, false);

    private final String name;
    private final Short value;
    private final Boolean flag;


    EnumAuthType(String name, Short value, Boolean flag) {
        this.name = name;
        this.value = value;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public Short getValue() {
        return value;
    }


    public Boolean getFlag() {
        return flag;
    }

    public static EnumAuthType get(Short value) {
        EnumAuthType[] enumAuthTypes = null;
        int length = (enumAuthTypes = values()).length;
        for (int i = 0; i < length; ++i) {
            EnumAuthType enumAuthType = enumAuthTypes[i];
            if (value.equals(enumAuthType.getValue())) {
                return enumAuthType;
            }
        }
        return getDefault();
    }

    public static EnumAuthType getDefault() {
        EnumAuthType defaultObject = null;
        EnumAuthType[] enumAuthTypes;
        int length = (enumAuthTypes = values()).length;

        for (int i = 0; i < length; ++i) {
            EnumAuthType defaultObject1 = enumAuthTypes[i];
            defaultObject = defaultObject1;
            if (defaultObject1.getFlag()) {
                return defaultObject1;
            }
        }
        return defaultObject;
    }

}
