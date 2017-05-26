package com.hrp.entity.system;

import java.io.Serializable;

/**
 * Button
 * 按钮实体类  对应页面上的 button、a、input
 *  button  -    id="departmentModifyBtn type="button" class="btn"
 *  a       -    id="userSmsBtn" title="批量发送短信" class="btn"
 *  input   -    id="xxxBtn" type="button"
 * @author KVLT
 * @date 2017-03-14.
 */
public class Button implements Serializable, Comparable<Button> {

    private Integer id;  // 按钮ID
    private String btnTag;  // 按钮标记 button a input
    private String btnId;  // 按钮ID
    private String btnType;  // 按钮类型
    private String btnTitle;  // 标题
    private String btnText;  // 内容
    private String btnClass;  // class
    private Integer menuId;  // 按钮所属菜单
    private boolean visible = true;  // 是否可见， 默认可见
    private boolean disabled = false;  // 是否可用，默认可用
    private String remark;  // 备注
    private Menu menu;  // 按钮所属菜单

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBtnTag() {
        return btnTag;
    }

    public void setBtnTag(String btnTag) {
        this.btnTag = btnTag;
    }

    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    public String getBtnType() {
        return btnType;
    }

    public void setBtnType(String btnType) {
        this.btnType = btnType;
    }

    public String getBtnTitle() {
        return btnTitle;
    }

    public void setBtnTitle(String btnTitle) {
        this.btnTitle = btnTitle;
    }

    public String getBtnClass() {
        return btnClass;
    }

    public void setBtnClass(String btnClass) {
        this.btnClass = btnClass;
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

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    @Override
    public int compareTo(Button o) {
        if (o != null) {
            if (this.getId() == o.getId()) {
                return 0;
            }
            return (this.getBtnId()).compareTo(o.getBtnId());
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Button button = (Button) o;

        if (!id.equals(button.id)) return false;
        return btnId.equals(button.btnId);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + btnId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Button{" +
                "id=" + id +
                ", btnTag='" + btnTag + '\'' +
                ", btnId='" + btnId + '\'' +
                ", btnType='" + btnType + '\'' +
                ", btnTitle='" + btnTitle + '\'' +
                ", btnText='" + btnText + '\'' +
                ", btnClass='" + btnClass + '\'' +
                ", menuId=" + menuId +
                ", visible=" + visible +
                ", disabled=" + disabled +
                ", remark='" + remark + '\'' +
                ", menu=" + menu +
                '}';
    }
}
