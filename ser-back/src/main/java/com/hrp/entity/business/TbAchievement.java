package com.hrp.entity.business;

import java.io.Serializable;
import java.util.Date;

/**
 * TbAchievement
 * 项目成果
 * @author KVLT
 * @date 2017-05-15.
 */
public class TbAchievement implements Serializable {
    private Integer id;
    private String achCode;  // 成果编号
    private String achName;  // 成果名称
    private String achTypeCode;  // 成果类型编号
    private String researchFields;  // 研究方向
    private Date recordTime;  // 登记时间
    private double achFunds;  //  成果经费
    private boolean finished;  //  是否结题

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAchCode() {
        return achCode;
    }

    public void setAchCode(String achCode) {
        this.achCode = achCode;
    }

    public String getAchName() {
        return achName;
    }

    public void setAchName(String achName) {
        this.achName = achName;
    }

    public String getAchTypeCode() {
        return achTypeCode;
    }

    public void setAchTypeCode(String achTypeCode) {
        this.achTypeCode = achTypeCode;
    }

    public String getResearchFields() {
        return researchFields;
    }

    public void setResearchFields(String researchFields) {
        this.researchFields = researchFields;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public double getAchFunds() {
        return achFunds;
    }

    public void setAchFunds(double achFunds) {
        this.achFunds = achFunds;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbAchievement that = (TbAchievement) o;

        if (Double.compare(that.achFunds, achFunds) != 0) return false;
        if (finished != that.finished) return false;
        if (!id.equals(that.id)) return false;
        if (!achCode.equals(that.achCode)) return false;
        if (!achName.equals(that.achName)) return false;
        if (achTypeCode != null ? !achTypeCode.equals(that.achTypeCode) : that.achTypeCode != null) return false;
        if (researchFields != null ? !researchFields.equals(that.researchFields) : that.researchFields != null)
            return false;
        return recordTime != null ? recordTime.equals(that.recordTime) : that.recordTime == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + achCode.hashCode();
        result = 31 * result + achName.hashCode();
        result = 31 * result + (achTypeCode != null ? achTypeCode.hashCode() : 0);
        result = 31 * result + (researchFields != null ? researchFields.hashCode() : 0);
        result = 31 * result + (recordTime != null ? recordTime.hashCode() : 0);
        temp = Double.doubleToLongBits(achFunds);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (finished ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TbAchievement{" +
                "id=" + id +
                ", achCode='" + achCode + '\'' +
                ", achName='" + achName + '\'' +
                ", achTypeCode='" + achTypeCode + '\'' +
                ", researchFields='" + researchFields + '\'' +
                ", recordTime=" + recordTime +
                ", achFunds=" + achFunds +
                ", finished=" + finished +
                '}';
    }
}
