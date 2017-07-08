package com.hrp.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hrp.annotation.MvcMapping;
import com.hrp.entity.system.Button;
import com.hrp.utils.JsoupUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
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

        ApplicationContext context = this.getContext();

        JsoupUtil ju = JsoupUtil.newInstance();

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
                        String url = mvcMapping.tag();
                        String path = mvcMapping.type().getPrefix() + mvcMapping.path() + mvcMapping.type().getSuffix();
                        mvcMap.put(url, path);
                    }
                }
            }

            String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
            String _path = path.replace('/', '\\')
                    .replace("file:", "")
                    .replace("\\test-classes\\", "")
                    .substring(1)
                    + "\\ser";

            logger.info(" ----- 项目根路径： " + _path + " -----------");

            List<Reflect2Path> list = Lists.newArrayList();
            String url = "";
            String filePath = "";

            Set<Map.Entry<String, String>> mvcSet = mvcMap.entrySet();
            Iterator<Map.Entry<String, String>> iter = mvcSet.iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> me = iter.next();
                logger.info(me.getKey() + "\t" + me.getValue());

                url = me.getKey();
                filePath = _path + me.getValue().replace("/", "\\");
                logger.info("解析jsp文件路径： " + filePath);

                List<Button> btns = Lists.newArrayList();
                try {
                    btns = ju.parseBtnByFile(filePath, "button");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //if (names != null && names.size() > 0) {
                    Reflect2Path rp = new Reflect2Path();
                    rp.setUrl(url);
                    rp.setView(me.getValue());
                    rp.setBtns(btns);

                    list.add(rp);
                //}
            }

            for (Reflect2Path rp : list) {
                logger.info("--- " + rp.getUrl()  + "\t" + rp.getView());
                logger.info("------------- 对应的按钮： ");

                String p = "-------------- ";
                for (Button btn : rp.getBtns()) {
                    p += btn.getBtnId() + " ";
                }
                logger.info(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class Reflect2Path {
    private String url;
    private String view;
    private List<Button> btns;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public List<Button> getBtns() {
        return btns;
    }

    public void setBtns(List<Button> btns) {
        this.btns = btns;
    }
}
