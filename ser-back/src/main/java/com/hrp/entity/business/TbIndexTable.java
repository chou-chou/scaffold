package com.hrp.entity.business;

import java.io.Serializable;

/**
 * TbProjectIndex
 * 项目指标表
 * @author KVLT
 *
 *
 */
public class TbIndexTable implements Serializable {
    private Integer id;  // ID 自增
    private String indexTableCode;  // 表编号
    private String indexTableName;  // 表名称
    private String creator;  // 表创造者
    private String remark;  // 表说明
    private String indexId;  // 指标id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndexTableCode() {
        return indexTableCode;
    }

    public void setIndexTableCode(String indexTableCode) {
        this.indexTableCode = indexTableCode;
    }

    public String getIndexTableName() {
        return indexTableName;
    }

    public void setIndexTableName(String indexTableName) {
        this.indexTableName = indexTableName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }
}
