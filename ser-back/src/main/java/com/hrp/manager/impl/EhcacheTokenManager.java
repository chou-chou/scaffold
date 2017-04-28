package com.hrp.manager.impl;

import com.hrp.shiro.token.TokenManager;
import org.apache.shiro.session.Session;
import org.springframework.cache.Cache;

/**
 * EhcacheTokenManager
 *
 * @author KVLT
 * @date 2017-03-28.
 */
public class EhcacheTokenManager implements TokenManager {

    private Cache userEhCache;

    @Override
    public Session getSession() {
        return null;
    }

    @Override
    public Object getVal2Session(Object key) {
        return null;
    }

    @Override
    public void setVal2Session(Object key, Object value) {

    }

    @Override
    public Long getUserId() {
        return null;
    }
}
