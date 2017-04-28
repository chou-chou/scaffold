package com.hrp.entity.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * EnumApplyStatus
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public enum EnumApplyStatus {
    SAVE(0,"保存"),RETURN(1,"退回"),SUBMIT(2,"医务审核"),ACCEPT(3,"接诊处理"),VISIT(4,"就诊确认"),COMPLETE(5,"完成");

    private Integer code;
    private String name;

    EnumApplyStatus(Integer code, String name) {
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
        for (EnumApplyStatus type : EnumApplyStatus.values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }
}
