package com.hrp.utils;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * PageData
 * 参数封装Map
 * @author KVLT
 * @date 2017-03-14.
 */
public class PageData extends HashMap implements Map {

    private static final long serialVersionUID = 1L;

    Map map = null;
    HttpServletRequest request;
    public PageData(HttpServletRequest request){
        this.request = request;
        Map properties = request.getParameterMap();
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        map = returnMap;
    }

    public PageData() {
        map = new HashMap();
    }

    @Override
    public Object get(Object key) {
        Object obj = null;
        if(map.get(key) instanceof Object[]) {
            Object[] arr = (Object[])map.get(key);
            obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
        } else {
            obj = map.get(key);
        }
        return obj;
    }

    public String getString(Object key) {
        return (String)get(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    public void clear() {
        map.clear();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Set entrySet() {
        return map.entrySet();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set keySet() {
        return map.keySet();
    }

    @SuppressWarnings("unchecked")
    public void putAll(Map t) {
        map.putAll(t);
    }

    public int size() {
        return map.size();
    }

    public Collection values() {
        return map.values();
    }

    public void print() {
        for (Object key : map.keySet()) {
            System.out.println("KEY : " + key + "\tVALUE : " + map.get(key));
        }
    }

    /**
     * 转换成bean，使用泛型免去了类型转换的麻烦
     * @param beanClass
     * @param <T>
     * @return
     */
    public <T> T map2Bean(Class<T> beanClass) {
        T bean = null;
        try {
            bean = beanClass.newInstance();  // 实例化bean类型
            BeanUtils.populate(bean, this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        }

        return bean;
    }

    /**
     * bean转换成Map
     * @param beanClass
     * @param <T>
     */
    public <T> void bean2Map(T beanClass) {
        try {
            this.map = BeanUtils.describe(beanClass);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        }
    }
}
