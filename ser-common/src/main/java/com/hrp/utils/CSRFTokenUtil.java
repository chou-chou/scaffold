package com.hrp.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * CSRFTokenUtil
 *
 * @author KVLT
 * @date 2017-03-23.
 */
public class CSRFTokenUtil {
    public static String generate(HttpServletRequest request) {

        return UUID.randomUUID().toString();
    }
}
