package com.hrp.listener;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hrp.annotation.MvcMapping;
import com.hrp.dao.impl.BaseDaoImpl;
import com.hrp.entity.system.Button;
import com.hrp.entity.system.Menu;
import com.hrp.utils.Constant;
import com.hrp.utils.JsoupUtil;
import com.hrp.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * MvcMappingListener
 * 扫描资源-视图映射关系
 */
public class MvcMappingListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MvcMappingListener.class);

    Map<String, Integer> map = new HashMap();
    List<Button> btnList = Lists.newArrayList();
    List<Button> btnInner = null;

    List<Button> btnToAdd = Lists.newArrayList();
    List<Button> btnToUpdate = Lists.newArrayList();
    List<Button> btnToDelete = Lists.newArrayList();

    BaseDaoImpl dao = null;
    PageData pd = new PageData();

	public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("----------------Spring容器初始化完毕-------------------");

        JsoupUtil ju = JsoupUtil.newInstance();
        ConcurrentMap<String, String> mvcMap = Maps.newConcurrentMap();
        try {
            // 获取上下文
            ApplicationContext context = event.getApplicationContext();

            dao = (BaseDaoImpl) context.getBean("baseDao");

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
                        mvcMap.put(tag != null ? tag.toLowerCase() : "", path);  // 类似 button:main - back/system/button/button_main.jsp
                    }
                }
            }

            logger.info("---------打印Mapping关系---------");
            Constant.MVC_MAP = mvcMap;

            String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
            String _path = path.replace('/', '\\')
                    .replace("file:", "")
                    .replace("\\test-classes\\", "")
                    .substring(1);

            logger.info(" -----------" + path + " ----------------");

            String rootPath = System.getProperty("ser.root");
            logger.info(" ----- 项目根路径： " +  rootPath + " -----------");

            List<Menu> menuList = (List<Menu>) dao.findForList("MenuMapper.listAllMenu", null);
            logger.info("获取到菜单数目：\t" + menuList.size());

            for (Menu menu : menuList) {
                map.put(menu.getMenuTag() != null ? menu.getMenuTag().toLowerCase() : "", menu.getMenuId()); // button:main - 19
            }

            String tag = "";
            String filePath = "";

            Set<Map.Entry<String, String>> mvcSet = mvcMap.entrySet();
            Iterator<Map.Entry<String, String>> iter = mvcSet.iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> me = iter.next();
                logger.info(me.getKey() + "\t" + me.getValue());

                tag = me.getKey();
                filePath = rootPath + me.getValue().replace("/", "\\");
                logger.info("解析jsp文件路径： " + filePath);

                List<Button> btns = Lists.newArrayList();
                try {
                    btns = ju.parseBtnByFile(filePath, Constant.IN_CONTROL);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Integer menuId = map.get(tag);

                for (Button btn : btns) {
                    btn.setMenuId(menuId);
                }

                btnList.addAll(btns);  // 遍历后所有的button
            }

            boolean flag = true;

            logger.info(" -------------- 向数据库中更新button数据 --------------");

            btnInner = (List<Button>) dao.findForList("ButtonMapper.getButtonList", pd);
            logger.info(" -------------- 数据库已有按钮: ----------------");
            for (Button button : btnInner) {
                logger.info(button.toString());
            }

            // 找出新增button
            for (Button button : btnList) {
                for (Button inner : btnInner) {
                    if (button.getBtnId() != null && button.getBtnId().equals(inner.getBtnId())) {
                        flag = false;
                        continue;
                    }
                }


                if (flag)
                    btnToAdd.add(button);

                flag = true;
            }

            flag = true;
            // 找出需要删除的button
            for (Button inner : btnInner) {
                for (Button button : btnList) {
                    if (inner.getBtnId() != null && inner.getBtnId().equals(button.getBtnId())) {
                        flag = false;
                        continue;
                    }
                }

                if (flag) btnToDelete.add(inner);

                flag = true;
            }

            logger.info("---------------新增button ---------------");
            for (Button button : btnToAdd) {
                logger.info(button.toString());
                dao.save("ButtonMapper.saveButton", button);
            }

            logger.info("---------------删除button ---------------");
            Set<Integer> ids = Sets.newHashSet();
            for (Button button : btnToDelete) {
                logger.info(button.toString());
                ids.add(button.getId());
            }

            if (ids.size() > 0) {

                pd.put("ids", ids);
                dao.delete("ButtonMapper.deleteButton", pd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
