package com.hrp.entity.system;

import java.io.Serializable;
import java.util.Date;

/**
 * TbTeam
 * 科研团队
 * @author KVLT
 * @date 2017-06-27
 */
public class TbTeam implements Serializable {
    private Integer teamId;  // ID 自增
    private String teamCode;  // 团队编号
    private String teamName;  // 团队名称
    private String teamType;  // 团队类型
    private Date createTime;  // 创建时间
    private String remark;  //备注
    private String userId;  // 成员id

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamType() {
        return teamType;
    }

    public void setTeamType(String teamType) {
        this.teamType = teamType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbTeam tbTeam = (TbTeam) o;

        if (teamId != null ? !teamId.equals(tbTeam.teamId) : tbTeam.teamId != null) return false;
        if (teamCode != null ? !teamCode.equals(tbTeam.teamCode) : tbTeam.teamCode != null) return false;
        if (teamName != null ? !teamName.equals(tbTeam.teamName) : tbTeam.teamName != null) return false;
        if (teamType != null ? !teamType.equals(tbTeam.teamType) : tbTeam.teamType != null) return false;
        if (createTime != null ? !createTime.equals(tbTeam.createTime) : tbTeam.createTime != null) return false;
        if (remark != null ? !remark.equals(tbTeam.remark) : tbTeam.remark != null) return false;
        return userId != null ? userId.equals(tbTeam.userId) : tbTeam.userId == null;
    }

    @Override
    public int hashCode() {
        int result = teamId != null ? teamId.hashCode() : 0;
        result = 31 * result + (teamCode != null ? teamCode.hashCode() : 0);
        result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
        result = 31 * result + (teamType != null ? teamType.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TbTeam{" +
                "teamId=" + teamId +
                ", teamCode='" + teamCode + '\'' +
                ", teamName='" + teamName + '\'' +
                ", teamType='" + teamType + '\'' +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
