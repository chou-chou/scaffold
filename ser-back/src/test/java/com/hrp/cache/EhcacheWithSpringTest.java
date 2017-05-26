package com.hrp.cache;

import com.google.common.collect.Lists;
import com.hrp.controller.BaseMvcTest;
import com.hrp.entity.system.Menu;
import com.hrp.service.MenuService;
import com.hrp.utils.PageData;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * EhcacheWithSpringTest
 * Spring + Ehcache 的缓存解决方案
 * @author KVLT
 * @date 2017-05-26.
 */
public class EhcacheWithSpringTest extends BaseMvcTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MenuService menuService;

    PageData pd = new PageData();

    @Test
    public void first() throws InterruptedException {
        Cache cache = cacheManager.getCache("menuCache");
        logger.info("添加元素");
        Element element = new Element("name", "test");
        cache.put(element);

        element = cache.get("name");
        logger.info("第一次获取： \n" + element.toString());

        Thread.sleep(1000);

        element = cache.get("name");
        System.out.println("第二次获取：\n"+element.toString());

        Thread.sleep(3000);
        element = cache.get("name");
        System.out.println("第三次获取：\n"+ element != null ? element.toString() : "");
    }

    @Test
    public void testListAllMenu() throws Exception {
        List<Menu> menus = Lists.newArrayList();

        logger.info(" ------ 第一次查询 ------ ");
        menus = menuService.listAllMenu();

        Thread.sleep(500);

        logger.info(" ------ 第二次查询 ------ ");
        menus = menuService.listAllMenu();


        // 在classpath:config/ehcache.xml中，设置了userCache的缓存时间为3000 ms, 这里设置等待
        Thread.sleep(3000);

        logger.info("\n缓存过期，再次查询!");
        logger.info(" ------ 第三次查询 ------ ");
        menus = menuService.listAllMenu();
    }

}
