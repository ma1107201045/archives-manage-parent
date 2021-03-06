package com.yintu.rixing.enumobject;

/**
 * @Author: mlf
 * @Date: 2021/1/23 10:20:05
 * @Version: 1.0
 */
public enum EnumArchivesLibraryDefaultField {

    ID("主键id", 1, "id", ""),
    CREATE_BY("创建人", 2, "create_by", ""),
    CREATE_TIME("创建时间", 3, "create_time", ""),
    MODIFIED_BY("修改人", 4, "modified_by", ""),
    MODIFIED_TIME("修改时间", 5, "modified_time", ""),
    ARCHIVES_CODE("档号", 6, "archives_code", ""),
    TOPIC_NAME("题名", 7, "topic_name", ""),
    RETENTION_PERIOD("保管期限", 8, "retention_period", ""),
    VALID_PERIOD("有效期", 9, "valid_period", ""),
    SECURITY_LEVEL("密级", 10, "security_level", ""),
    FILING_ANNUAL("归档年度", 11, "filing_annual", ""),
    DEPARTMENT_ID("组织机构", 12, "department_id", ""),
    STATUS("档案状态", 13, "status", "档案状态：0.档案收集 1.临时库 2.整理库 3.正式库 4.回收站 5.销毁库  6.病档管理"),
    STATUS_FIELD1("档案状态预留字段1", 14, "status_field1", ""),
    OPERATION_TIME_FIELD1("操作时间预留字段1", 15, "operation_time _field1", ""),
    STATUS_FIELD2("档案状态预留字段2", 16, "status_field2", ""),
    OPERATION_TIME_FIELD2("档案状态预留字段2", 17, "operation_time _field2", ""),
    DISEASE("是否病档", 18, "disease", "是否病档(1:是;0:不是)"),
    DISEASE_REMARK("病档备注", 19, "disease_remark", ""),
    ROLLBACK_REMARK("回退备注", 20, "rollback_remark", "");

    private final String name;
    private final Integer value;
    private final String dataKey;
    private final String description;

    EnumArchivesLibraryDefaultField(String name, Integer value, String dataKey, String description) {
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

    public static EnumArchivesLibraryDefaultField get(Integer value) {
        EnumArchivesLibraryDefaultField[] enumDefaultFields;
        int length = (enumDefaultFields = values()).length;
        for (int i = 0; i < length; ++i) {
            EnumArchivesLibraryDefaultField enumDataType = enumDefaultFields[i];
            if (value.equals(enumDataType.getValue())) {
                return enumDataType;
            }
        }
        return null;
    }
}
