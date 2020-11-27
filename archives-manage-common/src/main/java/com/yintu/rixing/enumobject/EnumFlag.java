package com.yintu.rixing.enumobject;

/**
 * @author:mlf
 * @date:2020/8/11 10:03
 */
public enum EnumFlag {

    FlagTrue("是", (short) 1, true), FlagFalse("否", (short) 0, true);

    private String name;

    private Short value;

    private Boolean flag;

    EnumFlag(String name, Short value, Boolean flag) {
        this.name = name;
        this.value = value;
        this.flag = flag;
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

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public static EnumFlag get(Short value) {
        EnumFlag[] enumFlags = null;
        int length = (enumFlags = values()).length;
        for (int i = 0; i < length; ++i) {
            EnumFlag enumFlag = enumFlags[i];
            if (value.equals(enumFlag.getValue())) {
                return enumFlag;
            }
        }
        return getDefault();
    }

    public static EnumFlag getDefault() {
        EnumFlag defaultObject = null;
        EnumFlag[] enumFlags;
        int length = (enumFlags = values()).length;
        for (int i = 0; i < length; ++i) {
            EnumFlag defaultObject1 = enumFlags[i];
            defaultObject = defaultObject1;
            if (defaultObject1.getFlag()) {
                return defaultObject1;
            }
        }
        return defaultObject;
    }
}
