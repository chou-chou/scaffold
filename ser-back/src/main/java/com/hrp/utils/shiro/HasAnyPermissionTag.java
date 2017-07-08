package com.hrp.utils.shiro;

import org.apache.shiro.web.tags.PermissionTag;

/**
 * HasAnyPermissionTag
 * Shiro 权限标签（JSP版）
 *
 * @author KVLT
 * @date 2017-06-27.
 */
public class HasAnyPermissionTag extends PermissionTag {

    private static final long serialVersionUID = 1L;
    private static final String PERMISSION_NAMES_DELIMETER = ",";

    /*private String permission;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }*/

    public HasAnyPermissionTag() {
    }

    /**
     * 是否拥有该权限
     *
     * @param permission    权限标识，用','分割
     * @return true: 是  false：否
     */
    protected boolean showTagBody(String permission) {
            boolean hasAnyPermission = false;
//        Subject subject = getSubject();
//        if (null != subject) {
            for (String perm : permission.split(PERMISSION_NAMES_DELIMETER)) {
//                if (subject.isPermitted(perm.trim())) {
                if (this.isPermitted(perm.trim())) {
                    hasAnyPermission = true;
                    break;
                }
            }
//        }
        return hasAnyPermission;
    }

}
