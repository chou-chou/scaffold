package com.hrp.entity.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * EnumIsComplete
 * 是否认证（0：否；1：是）
 * @author KVLT
 * @date 2017-03-14.
 */
public enum EnumIsComplete {
    YES(0,"否"),NO(1,"是");

    private Integer code;
    private String name;

    EnumIsComplete(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> enumDataMap = new TreeMap<Integer, String>();
        for (EnumIsComplete type : EnumIsComplete.values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }
}
