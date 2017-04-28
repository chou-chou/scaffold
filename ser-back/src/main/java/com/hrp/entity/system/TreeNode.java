package com.hrp.entity.system;

import java.io.Serializable;

/**
 * TreeNode
 * 通用树形结点实体
 * @author KVLT
 * @date 2017-04-26.
 */
public class TreeNode implements Serializable {

    private String id;
    private String pId;  // 父
    private String code;  // 编码
    private String name;  // 名称
    private String icon;  // 图标
    private String url;  // 链接
    private Boolean leaf;  // 是否叶子
    private Integer no; // 序号
    private Integer lvl;  // 等级/级别
    private Boolean isParent;  // 是否父结点

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", leaf=" + leaf +
                ", no=" + no +
                ", lvl=" + lvl +
                ", isParent=" + isParent +
                '}';
    }
}
