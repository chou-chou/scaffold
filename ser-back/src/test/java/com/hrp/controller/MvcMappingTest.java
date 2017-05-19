package com.hrp.controller;

import com.google.common.collect.Maps;
import com.hrp.annotation.MvcMapping;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * MvcMappingTest
 *
 * @author KVLT
 * @date 2017-05-19.
 */
public class MvcMappingTest extends BaseMvcTest {

    @Test
    public void mapping() {
        logger.info("----------------所有Bean载入完成-------------------");

        ConcurrentMap<String, String> mvcMap = Maps.newConcurrentMap();
        try {
            // 获取上下文

            // 获取所有beanNames
            String[] beanNames = context.getBeanNamesForType(Object.class);
            for (String beanName : beanNames) {
                Method[] methods = context.getBean(beanName).getClass()
                        .getDeclaredMethods();
                for (Method method : methods) {
                    // 判断该方法是否有MvcMapping注解
                    if (method.isAnnotationPresent(MvcMapping.class)) {
                        MvcMapping mvcMapping = method.getAnnotation(MvcMapping.class);
                        String url = mvcMapping.url();
                        String path = mvcMapping.type().getPrefix() + mvcMapping.path() + mvcMapping.type().getSuffix();
                        mvcMap.put(url, path);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<Map.Entry<String, String>> mvcSet = mvcMap.entrySet();
        Iterator<Map.Entry<String, String>> iter = mvcSet.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> me = iter.next();
            logger.info(me.getKey() + "\t" + me.getValue());
        }
    }
}
