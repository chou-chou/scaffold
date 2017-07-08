package com.hrp.exception;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;


/**
 * ExceptionI18Message
 * 异常信息国际化
 *
 * @author KVLT
 * @date 2017-06-19.
 */
public class ExceptionI18Message {
    public static String getLocaleMessage(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RequestContext requestContext = new RequestContext(request);
        return requestContext.getMessage(key);
    }
}
