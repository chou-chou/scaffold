package com.hrp.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlets
 *
 * @author KVLT
 * @date 2017-04-19.
 */
public class Servlets {

    /**
     * 是否是Ajax异步请求
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request){

        String accept = request.getHeader("accept");
        String xRequestedWith = request.getHeader("X-Requested-With");

        // 如果是异步请求，则直接返回信息
        return ((accept != null && accept.indexOf("application/json") != -1 )
                || (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1));
    }

}
