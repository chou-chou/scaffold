package com.hrp.shiro.cache;

import org.apache.shiro.cache.Cache;

/**
 * ShiroCacheManager
 * shiro cache manager 接口
 * @author KVLT
 * @date 2017-03-28.
 */
public interface ShiroCacheManager {

    <K, V> Cache<K, V> getCache(String name);

    void destroy();

}
