package com.hrp.utils;

import java.util.UUID;

/**
 * UuidUtil
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public class UuidUtil {

    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }
    public static void main(String[] args) {
        System.out.println(get32UUID());
    }
}
