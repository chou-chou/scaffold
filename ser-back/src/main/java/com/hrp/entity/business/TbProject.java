package com.hrp.entity.business;

import java.io.Serializable;
import java.util.Date;

/**
 * TbProject
 * 科研项目
 * @author KVLT
 * @date 2017-05-15.
 */
public class TbProject implements Serializable {
    private Integer id;  // ID 自增
    private String proCode;  // 项目编号
    private String proName;  // 项目名称
    private String proType;  // 项目类型 - 数据来源数据字典
    private String reportor;  // 项目申报人 - 申报人唯一，参与者不唯一
    private Date reportTime;  // 申报时间
    private Double declareFunds;  // 申报经费

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getReportor() {
        return reportor;
    }

    public void setReportor(String reportor) {
        this.reportor = reportor;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Double getDeclareFunds() {
        return declareFunds;
    }

    public void setDeclareFunds(Double declareFunds) {
        this.declareFunds = declareFunds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbProject tbProject = (TbProject) o;

        if (!id.equals(tbProject.id)) return false;
        if (!proCode.equals(tbProject.proCode)) return false;
        if (!proName.equals(tbProject.proName)) return false;
        if (proType != null ? !proType.equals(tbProject.proType) : tbProject.proType != null) return false;
        if (reportor != null ? !reportor.equals(tbProject.reportor) : tbProject.reportor != null) return false;
        if (reportTime != null ? !reportTime.equals(tbProject.reportTime) : tbProject.reportTime != null) return false;
        return declareFunds != null ? declareFunds.equals(tbProject.declareFunds) : tbProject.declareFunds == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + proCode.hashCode();
        result = 31 * result + proName.hashCode();
        result = 31 * result + (proType != null ? proType.hashCode() : 0);
        result = 31 * result + (reportor != null ? reportor.hashCode() : 0);
        result = 31 * result + (reportTime != null ? reportTime.hashCode() : 0);
        result = 31 * result + (declareFunds != null ? declareFunds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TbProject{" +
                "id=" + id +
                ", proCode='" + proCode + '\'' +
                ", proName='" + proName + '\'' +
                ", proType='" + proType + '\'' +
                ", reportor='" + reportor + '\'' +
                ", reportTime=" + reportTime +
                ", declareFunds=" + declareFunds +
                '}';
    }
}
