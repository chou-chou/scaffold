package com.hrp.oauth.entity;

import java.util.Date;

/**
 * OAuthUser
 *
 * @author KVLT
 * @date 2017-03-24.
 */
public class OAuthUser {

    private String id;
    private String userId;
    private String userName;
    private String oAuthType;
    private String oAuthId;
    private Date createTime;
    private Date updateTime;
    private String version;
    private Boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getoAuthType() {
        return oAuthType;
    }

    public void setoAuthType(String oAuthType) {
        this.oAuthType = oAuthType;
    }

    public String getoAuthId() {
        return oAuthId;
    }

    public void setoAuthId(String oAuthId) {
        this.oAuthId = oAuthId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
