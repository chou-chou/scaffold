package com.hrp.entity.system;

import java.io.Serializable;
import java.util.Date;

/**
 * UserDept
 * 用户部门
 * @author KVLT
 * @date 2017-06-27
 */
public class UserDept implements Serializable {
    private Integer id;  // ID 自增
    private String deptId;  // 部门id
    private String userId;  // 用户编号
    private String[] deptArr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String[] getDeptArr() {
        return deptArr;
    }

    public void setDeptArr(String[] deptArr) {
        this.deptArr = deptArr;
    }

}
