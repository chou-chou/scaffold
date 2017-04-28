package com.hrp.entity.system;

import com.hrp.utils.Constant;

import java.io.Serializable;
import java.util.List;

/**
 * User
 * 类名称： 用户
 * @author KVLT
 * @date 2017-03-14.
 */
public class User implements Serializable {

    private String userId;  // 用户编码
    private String username;  // 用户名称（中文）
    private String account;  // 用户账号（英文）
    private String nickname;  // 昵称
    private String password;  // 面貌
    private boolean expired;  // 是否过期
    private boolean locked;  // 是否冻结
    private boolean credentialsExpired;  // 是否证书有效
    private boolean enabled;  // 是否有效
    private String remark;  // 备注
    private String lastLogin;  // 最后登录时间

    private String salt; // 盐钥
    private String credentialsSalt;

    private List<Role> roleList;  // 所属角色
    private List<Department> deptList;  // 管辖部门

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getSalt() {
        return username + Constant.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Department> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }

    public void setCredentialsSalt(String credentialsSalt) {
        this.credentialsSalt = credentialsSalt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", expired=" + expired +
                ", locked=" + locked +
                ", credentialsExpired=" + credentialsExpired +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", salt='" + salt + '\'' +
                ", roleList=" + roleList +
                ", deptList=" + deptList +
                '}';
    }
}
