package com.hrp.utils.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * CacheStore
 * cache管理器
 * @author KVLT
 * @date 2017-04-17.
 */
public class CacheStore {

    private static CacheManager manager;
    private static Cache cache;
    static {
        CacheStore cs = new CacheStore();
        cs.init();
    }

    /**
     * 初试化cache
     */
    private void init() {
        URL url = getClass().getResource("/config/context/ehcache.xml");
        manager = new CacheManager(url);
        cache = manager.getCache("UrlCache");
    }

    /**
     * 清除cache
     */
    @SuppressWarnings("unused")
    private void destory() {
        manager.shutdown();
    }

    /**
     * 得到某个key的cache值
     *
     * @param key
     * @return
     */
    public static Element get(String key) {
        return cache.get(key);
    }

    /**
     * 清楚key的cache
     *
     * @param key
     */
    public static void remove(String key) {
        cache.remove(key);
    }

    /**
     * 设置或更新某个cache值
     *
     * @param element
     */
    public static void put(Element element) {
        cache.put(element);
    }

    public static void removeAll(){
        cache.removeAll();
    }



    public static void main(String[] args) {
        Map m = new HashMap();
        m.put("1", "1");
        m.put("2", "2");
        m.put("3", "3");
        Map m1 = new HashMap();
        m1.put("11", "11");
        m1.put("21", "21");
        m1.put("31", "31");
        CacheStore.remove("1");
        System.out.println(CacheStore.get("1"));
        System.out.println(CacheStore.get("2"));
        System.out.println(CacheStore.get("2"));
        CacheStore.removeAll();
        System.out.println(CacheStore.get("2"));
        System.out.println(CacheStore.get("3"));
        System.out.println("------end-----");
    }

}
