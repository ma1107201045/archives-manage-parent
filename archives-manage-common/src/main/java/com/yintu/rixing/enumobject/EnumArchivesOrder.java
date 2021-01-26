package com.yintu.rixing.enumobject;

/**
 * @Author: mlf
 * @Date: 2021/1/18 10:38:42
 * @Version: 1.0
 */
public enum EnumArchivesOrder {

    ARCHIVES_COLLECTION("档案收集", (short) 0, ""),
    TEMPORARY_LIBRARY("临时库", (short) 1, ""),
    SORTING_LIBRARY("整理库", (short) 2, ""),
    FORMAL_LIBRARY("正式库", (short) 3, ""),
    RECYCLE_BIN("回收站", (short) 4, ""),
    DESTRUCTION_LIBRARY("销毁库", (short) 5, ""),
    DISEASE_ARCHIVES("病档管理", (short) 6, ""),
    ARCHIVES_DESTRUCTION("档案销毁", (short) 7, "");

    private final String name;
    private final Short value;
    private final String description;

    EnumArchivesOrder(String name, Short value, String description) {
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

    public static EnumArchivesOrder get(Short value) {
        EnumArchivesOrder[] enumArchivesOrders;
        int length = (enumArchivesOrders = values()).length;
        for (int i = 0; i < length; ++i) {
            EnumArchivesOrder enumArchivesOrder = enumArchivesOrders[i];
            if (value.equals(enumArchivesOrder.getValue())) {
                return enumArchivesOrder;
            }
        }
        return null;
    }

}
