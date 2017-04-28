package com.hrp.entity.system;

import java.io.Serializable;

/**
 * Button
 * 按钮实体类
 * @author KVLT
 * @date 2017-03-14.
 */
public class Button implements Serializable {

    private Integer btnId;  // 按钮ID
    private String btnName;  // 按钮名称
    private Integer menuId;  // 按钮所属菜单
    private boolean disabled;  // 是否可用
    private String remark;  // 备注
    private Menu menu;  // 按钮所属菜单

    public Integer getBtnId() {
        return btnId;
    }

    public void setBtnId(Integer btnId) {
        this.btnId = btnId;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
