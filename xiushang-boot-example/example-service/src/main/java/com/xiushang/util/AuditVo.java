package com.xiushang.util;

/**
 * 审核
 */
public class AuditVo implements java.io.Serializable {
    private String id;
    private String auditOption;
    private String auditType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuditOption() {
        return auditOption;
    }

    public void setAuditOption(String auditOption) {
        this.auditOption = auditOption;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public boolean getAgree() {
        return "1".equals(auditType);
    }
}
