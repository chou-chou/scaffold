package com.hrp.entity.business;

import java.io.Serializable;
import java.util.Date;

/**
 * TbAchAward
 * 项目成果奖励 （与项目成果 一对多）
 * @author KVLT
 * @date 2017-05-15.
 */
public class TbAchAward implements Serializable {
    private Integer id;
    private String awardCode;  // 奖励编号
    private String achCode;  // 成果编号 对应TB_ACHIEVEMENT中的ACH_CODE
    private Date awardTime;  // 获奖时间
    private String remark;  // 备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAwardCode() {
        return awardCode;
    }

    public void setAwardCode(String awardCode) {
        this.awardCode = awardCode;
    }

    public String getAchCode() {
        return achCode;
    }

    public void setAchCode(String achCode) {
        this.achCode = achCode;
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbAchAward that = (TbAchAward) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (awardCode != null ? !awardCode.equals(that.awardCode) : that.awardCode != null) return false;
        if (achCode != null ? !achCode.equals(that.achCode) : that.achCode != null) return false;
        if (awardTime != null ? !awardTime.equals(that.awardTime) : that.awardTime != null) return false;
        return remark != null ? remark.equals(that.remark) : that.remark == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (awardCode != null ? awardCode.hashCode() : 0);
        result = 31 * result + (achCode != null ? achCode.hashCode() : 0);
        result = 31 * result + (awardTime != null ? awardTime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TbAchAward{" +
                "id=" + id +
                ", awardCode='" + awardCode + '\'' +
                ", achCode='" + achCode + '\'' +
                ", awardTime=" + awardTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}
