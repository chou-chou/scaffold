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
    private String supId;  // 上级编码
    private Integer sequence = 0;  // 顺序
    private String  isLeaf;  // 是否叶子结点
    private String described;  // 部门职能描述
    private String address;  // 地址
    private String remark;  // 备注

    private Department supDepartment;  // 父部门
    private List<Department> supDepartments;  // 子部门集合

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

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
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

    public List<Department> getSupDepartments() {
        return supDepartments;
    }

    public void setSubDepartments(List<Department> supDepartments) {
        this.supDepartments = supDepartments;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String  getLeaf() {
        return isLeaf;
    }

    public void setLeaf(String   leaf) {
        isLeaf = leaf;
    }
}
