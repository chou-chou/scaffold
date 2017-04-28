package com.hrp.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * SessionUtil
 *
 * @author KVLT
 * @date 2017-03-23.
 */
public class SessionUtil {
    public static Session getSession() {

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        return session;
    }
}
