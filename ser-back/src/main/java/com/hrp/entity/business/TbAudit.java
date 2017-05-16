package com.hrp.entity.business;

import java.io.Serializable;

/**
 * TbAudit
 * 审核实体
 * @author KVLT
 * @date 2017-05-15.
 */
public class TbAudit implements Serializable {
    private Integer id;
    private String targetCode;  // 目标实体  编号
    private String targetType;  // 目标实体  类型
    private Integer auditNo;  // 审核序号
    private String auditor;  // 审核人
    private String auditStatus;  // 审核状态
    private String auditOpinion;  // 审核意见

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Integer getAuditNo() {
        return auditNo;
    }

    public void setAuditNo(Integer auditNo) {
        this.auditNo = auditNo;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbAudit tbAudit = (TbAudit) o;

        if (!id.equals(tbAudit.id)) return false;
        if (!targetCode.equals(tbAudit.targetCode)) return false;
        if (!targetType.equals(tbAudit.targetType)) return false;
        if (auditNo != null ? !auditNo.equals(tbAudit.auditNo) : tbAudit.auditNo != null) return false;
        if (auditor != null ? !auditor.equals(tbAudit.auditor) : tbAudit.auditor != null) return false;
        if (auditStatus != null ? !auditStatus.equals(tbAudit.auditStatus) : tbAudit.auditStatus != null) return false;
        return auditOpinion != null ? auditOpinion.equals(tbAudit.auditOpinion) : tbAudit.auditOpinion == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + targetCode.hashCode();
        result = 31 * result + targetType.hashCode();
        result = 31 * result + (auditNo != null ? auditNo.hashCode() : 0);
        result = 31 * result + (auditor != null ? auditor.hashCode() : 0);
        result = 31 * result + (auditStatus != null ? auditStatus.hashCode() : 0);
        result = 31 * result + (auditOpinion != null ? auditOpinion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TbAudit{" +
                "id=" + id +
                ", targetCode='" + targetCode + '\'' +
                ", targetType='" + targetType + '\'' +
                ", auditNo=" + auditNo +
                ", auditor='" + auditor + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", auditOpinion='" + auditOpinion + '\'' +
                '}';
    }
}
