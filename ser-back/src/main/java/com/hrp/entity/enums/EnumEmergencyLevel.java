package com.hrp.entity.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * EnumEmergencyLevel
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public enum EnumEmergencyLevel {
    COMMONLY(0,"一般"),EMERGENCY(1,"比较急"),HURRY(2,"非常急");

    private Integer code;
    private String name;

    EnumEmergencyLevel(Integer code, String name) {
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
        for (EnumEmergencyLevel type : EnumEmergencyLevel.values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }
}
