package com.hrp.entity.system;

import java.io.Serializable;
import java.util.Date;

/**
 * UserTeam
 * 团队成员
 * @author KVLT
 * @date 2017-06-27
 */
public class UserTeam implements Serializable {

    private Integer id;  // ID 自增
    private String userId;  // 用户编号
    private Integer teamId;  // 团队id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "UserTeam{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", teamId=" + teamId +
                '}';
    }
}