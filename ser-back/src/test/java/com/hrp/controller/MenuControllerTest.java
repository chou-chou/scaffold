package com.hrp.controller;

import com.hrp.entity.system.Menu;
import com.hrp.service.MenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * MenuControllerTest
 *
 * @author KVLT
 * @date 2017-06-01.
 */
public class MenuControllerTest extends BaseMvcTest {

    @Autowired
    private MenuService menuService;

    @Test
    @Rollback(false)
    public void update() throws Exception {
        Menu menu = new Menu();
        menu.setMenuId(93);
        menu.setMenuName("测试_" + (Math.random() * 100 / 1));

        menuService.updateMenu(menu);
    }
}
