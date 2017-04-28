package com.hrp.entity.system;

import java.io.Serializable;
import java.util.List;

/**
 * Department
 * 组织结构
 * @author KVLT
 * @date 2017-03-14.
 */
public class Department implements Serializable {

    private Integer deptId;  // 部门ID
    private String deptCode; // 部门编码
    private String deptName;  // 部门名称
    private Integer supId;  // 上级编码
    private String function;  // 部门职能
    private String address;  // 地址
    private String remark;  // 备注

    private Department supDepartment;  // 父部门
    private List<Department> subDepartments;  // 子部门集合

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getSupId() {
        return supId;
    }

    public void setSupId(Integer supId) {
        this.supId = supId;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Department getSupDepartment() {
        return supDepartment;
    }

    public void setSupDepartment(Department supDepartment) {
        this.supDepartment = supDepartment;
    }

    public List<Department> getSubDepartments() {
        return subDepartments;
    }

    public void setSubDepartments(List<Department> subDepartments) {
        this.subDepartments = subDepartments;
    }
}
