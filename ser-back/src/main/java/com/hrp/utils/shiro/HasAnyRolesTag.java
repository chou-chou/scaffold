package com.hrp.utils.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.RoleTag;

/**
 * HasAnyRolesTag
 * Shiro 角色权限控制
 *
 * @author KVLT
 * @date 2017-06-27.
 */
public class HasAnyRolesTag extends RoleTag {

    private static final String ROLE_NAMES_DELIMETER = ",";

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public HasAnyRolesTag() {
    }

    /**
     * 是否拥有该角色
     *
     * @param role 角色名称，多个以“，”分割
     * @return
     */
    protected boolean showTagBody(String role) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();
        if (subject != null) {
            for (String r : role.split(ROLE_NAMES_DELIMETER)) {
                if (subject.hasRole(r.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }
        return hasAnyRole;
    }
}
