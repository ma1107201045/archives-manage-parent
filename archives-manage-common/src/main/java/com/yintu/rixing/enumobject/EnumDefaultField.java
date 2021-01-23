package com.yintu.rixing.enumobject;

/**
 * @Author: mlf
 * @Date: 2021/1/23 10:20:05
 * @Version: 1.0
 */
public enum EnumDefaultField {

    ID("主键id", 1, "id", ""),
    CREATE_BY("创建人", 2, "create_by", ""),
    CREATE_TIME("创建时间", 3, "create_time", ""),
    MODIFIED_BY("修改人", 4, "modified_by", ""),
    MODIFIED_TIME("修改时间", 5, "modified_time", ""),
    ARCHIVES_NUM("档号", 6, "archives_num", ""),
    STATUS("档案状态", 14, "status", "");

    private final String name;
    private final Integer value;
    private final String dataKey;
    private final String description;

    EnumDefaultField(String name, Integer value, String dataKey, String description) {
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

    public static EnumDefaultField get(Integer value) {
        EnumDefaultField[] enumDefaultFields;
        int length = (enumDefaultFields = values()).length;
        for (int i = 0; i < length; ++i) {
            EnumDefaultField enumDataType = enumDefaultFields[i];
            if (value.equals(enumDataType.getValue())) {
                return enumDataType;
            }
        }
        return null;
    }
}
