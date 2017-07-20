package com.hrp.entity.business;

import java.io.Serializable;
import java.util.Date;

/**
 * TbProjectIndex
 * 项目指标
 * @author KVLT
 * @date 2017-0-7
 */
public class TbProjectIndex implements Serializable {
    private Integer id;  // ID 自增
    private String indexCode;  // 项目指标编号
    private String indexName;  // 项目指标名称
    private String indexType;  // 项目指标类型
    private String indexScore;  // 项目指标总分
    private String remark;  // 项目指标说明
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getIndexScore() {
        return indexScore;
    }

    public void setIndexScore(String indexScore) {
        this.indexScore = indexScore;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
