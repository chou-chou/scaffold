package com.hrp.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hrp.annotation.MvcMapping;
import com.hrp.entity.system.Button;
import com.hrp.entity.system.Menu;
import com.hrp.utils.JsoupUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * ButtonControllerTests
 *
 * @author KVLT
 * @date 2017-05-24.
 */
public class ButtonControllerTests extends BaseMvcTest {

    ApplicationContext context = null;
    List<Menu> menuList = null;
    List<Button> btnList = Lists.newArrayList();
    List<Button> btnInner = null;

    List<Button> btnToAdd = Lists.newArrayList();
    List<Button> btnToUpdate = Lists.newArrayList();
    List<Button> btnToDelete = Lists.newArrayList();

    Map<String, Integer> map = new HashMap();

    @Before
    public void setup() {
        // 获取菜单信息
        try {
            context = this.getContext();

            menuList = (List<Menu>) dao.findForList("MenuMapper.listAllMenu", null);
            logger.info("获取到菜单数目：\t" + menuList.size());

            for (Menu menu : menuList) {
                map.put(menu.getMenuUrl(), menu.getMenuId());
            }

            btnInner = (List<Button>) dao.findForList("ButtonMapper.getButtonList", pd);
            for (Button button : btnInner) {
                logger.info(button.toString());
            }
        } catch (Exception e) {

        }
    }

    @Test
    public void fetchButtonInfo() {
        logger.info("----------------所有Bean载入完成-------------------");

        JsoupUtil ju = JsoupUtil.newInstance();

        ConcurrentMap<String, String> mvcMap = Maps.newConcurrentMap();
        try {
            String[] beanNames = context.getBeanNamesForType(Object.class);
            for (String beanName : beanNames) {
                Method[] methods = context.getBean(beanName).getClass()
                        .getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(MvcMapping.class)) {
                        MvcMapping mvcMapping = method.getAnnotation(MvcMapping.class);
                        String url = mvcMapping.url();
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
                    btns = ju.parseBtnByFile(filePath, ".inCtrl");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Integer menuId = map.get(url);

                for (Button btn : btns) {
                    btn.setMenuId(menuId);
                }

                btnList.addAll(btns);  // 遍历后所有的button
            }

            boolean flag = true;

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

                if (flag)
                btnToDelete.add(inner);

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

            pd.put("ids", ids);
            dao.delete("ButtonMapper.deleteButton", pd);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
