package com.hrp.entity.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * EnumElementType
 * 元素类型
 * @author KVLT
 * @date 2017-03-14.
 */
public enum EnumElementType {
    TEXT(0,"文本框"),TEXTAREA(1,"多行文本"),SELECT(2,"下拉菜单"),RADIO(3,"单选框"),CHECKBOX(4,"复选框");

    private Integer code;
    private String name;

    EnumElementType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> enumDataMap = new TreeMap<Integer, String>();
        for (EnumElementType type : EnumElementType.values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }
}
