package com.hrp.entity.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * EnumHospitalRank
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public enum EnumHospitalRank {
    RANK_FIRST(1,"一级医院"),RANK_SECOND(2,"二级医院"),RANK_THIRD(3,"三级医院");
    private Integer code;
    private String name;

    EnumHospitalRank(Integer code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> enumDataMap = new TreeMap<Integer, String>();
        for (EnumHospitalRank type : EnumHospitalRank.values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }
}
