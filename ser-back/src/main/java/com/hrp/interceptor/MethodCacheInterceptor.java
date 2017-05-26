package com.hrp.interceptor;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * MethodCacheInterceptor
 * 拦截器，用于缓存方法返回结果
 * @author KVLT
 * @date 2017-05-26.
 */
public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MethodCacheInterceptor.class);

    private Cache cache;

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();
        Object[] arguments = invocation.getArguments();
        Object result;

        String cacheKey = getCacheKey(targetName, methodName, arguments);
        Element element = cache.get(cacheKey);
        if (null == element) {
            result = invocation.proceed();

            element = new Element(cacheKey, (Serializable) result);
            cache.put(element);
        }

        return element.getObjectValue();
    }

    private String getCacheKey(String targetName, String methodName, Object[] arguments) {
        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append(".").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (Object argument : arguments) {
                sb.append(".").append(argument);
            }
        }

        return sb.toString();
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache,
                "Need a cache. Please use setCache(Cache) create it.");
    }


}
