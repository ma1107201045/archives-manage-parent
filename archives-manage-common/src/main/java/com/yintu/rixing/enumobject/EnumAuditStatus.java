package com.yintu.rixing.enumobject;

/**
 * @Author: mlf
 * @Date: 2021/2/1 14:48:09
 * @Version: 1.0
 */
public enum EnumAuditStatus {

    AUDIT_IN("审核中", (short) 1, ""),
    AUDIT_PASS("审核通过", (short) 2, ""),
    AUDIT_REFUSE("审核拒绝", (short) 3, "");

    private final String name;
    private final Short value;
    private final String description;

    EnumAuditStatus(String name, Short value, String description) {
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

    public static EnumAuditStatus get(Short value) {
        EnumAuditStatus[] enumAuditStatuses;
        int length = (enumAuditStatuses = values()).length;
        for (int i = 0; i < length; ++i) {
            EnumAuditStatus enumAuditStatus = enumAuditStatuses[i];
            if (value.equals(enumAuditStatus.getValue())) {
                return enumAuditStatus;
            }
        }
        return AUDIT_IN;
    }
}
