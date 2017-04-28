package com.hrp.entity.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Menu
 * 类名称： 菜单
 * @author KVLT
 * @date 2017-03-14.
 */
public class Menu implements Serializable {

    private Integer menuId;  // ID
    private String menuName;  // 菜单名称
    private String menuUrl;  // Action
    private Integer leaf;  // 是否叶子节点
    private Integer rank;  // 等级
    private Integer sequence;  // 顺序
    private Integer rootId;  // 根节点ID
    private String supId;  // 父节点ID
    private String icon;  // 图标
    private boolean disabled;  // 停用标识
    private String remark;  // 备注

    private Date modifyDate;  // 修改时间
    private String modifyUserId;  // 修改人ID
    private String modifyUsername;  // 修改人名称

    private Menu parentMenu;  // 父菜单
    private List<Menu> subMenu;  // 子菜单列表
    private boolean hasMenu = false;

    private List<Button> subButtons;  // 当前菜单对应的按钮

    public List<Button> getSubButtons() {
        return subButtons;
    }

    public void setSubButtons(List<Button> subButtons) {
        this.subButtons = subButtons;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public List<Menu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Menu> subMenu) {
        this.subMenu = subMenu;
    }

    public boolean isHasMenu() {
        return hasMenu;
    }

    public void setHasMenu(boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getModifyUsername() {
        return modifyUsername;
    }

    public void setModifyUsername(String modifyUsername) {
        this.modifyUsername = modifyUsername;
    }
}
