package com.hrp.entity.system;

/**
 * UserRoleLink
 * 描述User和Role之间的映射关系
 * @author KVLT
 * @date 2017-03-15.
 */
public class UserRoleLink {

    private User user;
    private Role role;
    private Integer id;
    private Boolean enabled;
    private String userId;
    private Integer roleId;

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "UserRoleLink{" +
                "user=" + user +
                ", role=" + role +
                ", id=" + id +
                ", enabled=" + enabled +
                ", userId='" + userId + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
