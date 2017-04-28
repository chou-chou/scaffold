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
    private boolean enabled;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
