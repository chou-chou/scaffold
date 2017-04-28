package com.hrp.entity.system;

import com.hrp.utils.tree.ITreeNode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dictionary
 * 字典实体
 * @author KVLT
 * @date 2017-03-14.
 */
public class Dictionary implements Serializable, ITreeNode {

    private Integer dicId;  // 字典ID
    private String entryCode;  // 编码
    private String entryValue;  // 名称
    private Integer sequence = 0;  // 顺序
    private Boolean isLeaf;  // 是否叶子结点
    private String supCode;  // 父entryCode
    private String classCode;  // 类别编码
    private String className;  // 类别名称
    private String additional;  // 附加字段
    private Boolean enabled;  // 是否可用
    private String remark;  // 备注

    private Dictionary supDic;  // 父字典
    private List<Dictionary> subDictionarys;  // 子字典集合

    public Integer getDicId() {
        return dicId;
    }

    public void setDicId(Integer dicId) {
        this.dicId = dicId;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public String getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Dictionary getSupDic() {
        return supDic;
    }

    public void setSupDic(Dictionary supDic) {
        this.supDic = supDic;
    }

    public List<Dictionary> getSubDictionarys() {
        return subDictionarys;
    }

    public void setSubDictionarys(List<Dictionary> subDictionarys) {
        this.subDictionarys = subDictionarys;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String parentIdGet() {
        return this.getSupCode();
    }

    @Override
    public String idGet() {
        return this.getEntryCode();
    }

    @Override
    public Map attributeForTreeGet() {
        Map map = new HashMap();
        map.put("id", this.getDicId());
        map.put("code", this.getEntryCode());
        map.put("name", this.getEntryValue());
        map.put("isleaf", this.getLeaf());
        map.put("level", this.getSequence());

        return map;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "dicId=" + dicId +
                ", entryCode='" + entryCode + '\'' +
                ", entryValue='" + entryValue + '\'' +
                ", sequence=" + sequence +
                ", isLeaf=" + isLeaf +
                ", supCode='" + supCode + '\'' +
                ", classCode='" + classCode + '\'' +
                ", className='" + className + '\'' +
                ", additional='" + additional + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", supDic=" + supDic +
                ", subDictionarys=" + subDictionarys +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
