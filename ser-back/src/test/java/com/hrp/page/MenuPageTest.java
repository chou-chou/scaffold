package com.hrp.page;

import com.hrp.dao.BaseDaoTest;
import com.hrp.entity.system.Menu;
import org.junit.Test;

import java.util.List;

/**
 * MenuPageTest
 *
 * @author KVLT
 * @date 2017-07-18.
 */
public class MenuPageTest extends BaseDaoTest {

    @Test
    public void menuListPage() throws Exception {
        page.setShowCount(10);
        page.setOrderColumn("MENU_TAG");
        page.setCurrentPage(1);

        List<Menu> list = (List<Menu>)dao.findForList("MenuMapper.menuListPage", page);
        for (Menu menu : list) {
            logger.info(menu.toString());
        }
    }
}
