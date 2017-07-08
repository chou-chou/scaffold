package com.hrp.listener;

import com.google.common.collect.Maps;
import com.hrp.annotation.MvcMapping;
import com.hrp.utils.Constant;
import com.hrp.utils.JsoupUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * MvcMappingListener
 * 扫描资源-视图映射关系
 */
public class MvcMappingListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MvcMappingListener.class);

	public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("----------------Spring容器初始化完毕-------------------");

        JsoupUtil ju = JsoupUtil.newInstance();
        ConcurrentMap<String, String> mvcMap = Maps.newConcurrentMap();
        try {
            // 获取上下文
            ApplicationContext context = event.getApplicationContext();

            // 获取所有beanNames
            String[] beanNames = context.getBeanNamesForType(Object.class);
            for (String beanName : beanNames) {
                Method[] methods = context.getBean(beanName).getClass()
                        .getDeclaredMethods();
                for (Method method : methods) {
                    // 判断该方法是否有MvcMapping注解
                    if (method.isAnnotationPresent(MvcMapping.class)) {
                        MvcMapping mvcMapping = method.getAnnotation(MvcMapping.class);
                        String tag = mvcMapping.tag();
                        String path = mvcMapping.type().getPrefix() + mvcMapping.path() + mvcMapping.type().getSuffix();
                        mvcMap.put(tag, path);
                    }
                }
            }

            logger.info("---------打印Mapping关系---------");
            Set<Map.Entry<String, String>> mvcSet = mvcMap.entrySet();
            Iterator<Map.Entry<String, String>> iter = mvcSet.iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> me = iter.next();
                logger.info(me.getKey() + "\t" + me.getValue());
            }

            Constant.MVC_MAP = mvcMap;

            String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
            path = path.replace('/', '\\')
                    .replace("file:", "")
                    .replace("classes\\", "");

            logger.info(" -----------" + path + " ----------------");

            String rootPath = System.getProperty("ser.root");
            logger.info(" ----- 项目根路径： " +  rootPath + " -----------");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
