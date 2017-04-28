package com.hrp.entity.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * EnumAgreement
 * 协议状态
 * @author KVLT
 * @date 2017-03-14.
 */
public enum EnumAgreement {
    EFFECTIVE(0, "有效"),DISABLE(1,"禁用"),DELETE(2,"删除");

    private Integer code;
    private String name;

    EnumAgreement(Integer code, String name) {
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
        for (EnumAgreement type : EnumAgreement.values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }
}
