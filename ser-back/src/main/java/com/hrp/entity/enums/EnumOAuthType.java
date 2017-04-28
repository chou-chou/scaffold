package com.hrp.entity.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * EnumOAuthType
 *
 * @author KVLT
 * @date 2017-03-24.
 */
public enum EnumOAuthType {
    GITHUB("github", "Github"),
    WECHAT("wechat", "微信"),
    QQ("qq", "QQ"),
    WEIBO("weibo", "微博"),
    BAIDU("baidu", "百度")
    ;

    private String code;
    private String name;

    EnumOAuthType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        for (EnumAgreementType type : EnumAgreementType.values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }

}
