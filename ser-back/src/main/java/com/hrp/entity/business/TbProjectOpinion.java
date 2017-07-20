package com.hrp.entity.business;

import java.io.Serializable;

/**
 * Created by Resource on 2017/7/17.
 */
public class TbProjectOpinion implements Serializable {
    private Integer id;
    private  Integer projectId;
    private  String userId;
    private  String indexTableId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIndexTableId() {
        return indexTableId;
    }

    public void setIndexTableId(String indexTableId) {
        this.indexTableId = indexTableId;
    }
}
