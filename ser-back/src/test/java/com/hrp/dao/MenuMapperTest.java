package com.hrp.dao;

import com.hrp.entity.system.Menu;
import com.hrp.utils.PageData;
import org.junit.Test;

import java.util.List;

/**
 * MenuMapperTest
 *
 * @author KVLT
 * @date 2017-03-15.
 */
public class MenuMapperTest extends BaseDaoTest {

    @Test
    public void testMenu() throws Exception{
        PageData pd = new PageData();
        Menu me = new Menu();
        //MENU_NAME,MENU_URL,LEAF,horizontal,sequence,ROOT_ID,SUP_ID,ICON,DISABLED,REMARK
        me.setMenuName("数据字典");
        me.setMenuUrl("/dic.jsp");
        me.setLeaf(true);
        me.setRank(1);
        me.setSequence(1);
        me.setRootId(0);
        me.setSupId(1);
        me.setIcon("1");
        //me.setDisabled(false);
        me.setRemark("1");
        dao.save("MenuMapper.saveMenuvity",me);
       // pd = (PageData)dao.findForObject("MenuMapper.getMenuCount", pd);
       // pd.print();
    }

    @Test
    public void listAllMenu() throws Exception {
        List<Menu> menuList = (List<Menu>) dao.findForList("MenuMapper.listAllMenu", null);

        logger.info("获取菜单记录数: " + menuList.size());

    }

    @Test
    public void getMenuById() throws Exception {
        pd.put("menuId", 1);
        Menu menu = (Menu) dao.findForObject("MenuMapper.getMenuById", 2);

        logger.info(menu.toString());
    }

    @Test
    public void saveMenu() throws Exception {
        Menu menu = new Menu();
        menu.setMenuName("test");
        menu.setRemark("test");
        menu.setMenuUrl("/u/t/root.do");
//        menu.setEnabled(true);

        Object obj = dao.save("MenuMapper.saveMenu", menu);
        //Assert.assertEquals("没有插入", 1, obj);
        logger.info(" --> " + menu.getMenuId());
    }
}
