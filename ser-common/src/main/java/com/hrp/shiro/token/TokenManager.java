package com.hrp.shiro.token;

import org.apache.shiro.session.Session;

/**
 * TokenManager
 *
 * @author KVLT
 * @date 2017-03-28.
 */
public interface TokenManager {

    public Session getSession();

    public Object getVal2Session(Object key);

    public void setVal2Session(Object key ,Object value);

    public Long getUserId();

}
