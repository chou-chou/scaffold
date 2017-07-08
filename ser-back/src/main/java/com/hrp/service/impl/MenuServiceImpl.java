package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.Menu;
import com.hrp.entity.system.TreeNode;
import com.hrp.service.MenuService;
import com.hrp.utils.PageData;
import io.swagger.models.auth.In;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * MenuServiceImpl
 *
 * @author KVLT
 * @date 2017-03-24.
 */
@Service("menuService")
@CacheConfig(cacheNames = "menuCache")
public class MenuServiceImpl implements MenuService {

    @Resource(name = "baseDao")
    private BaseDao baseDao;

    public Set<String> getMenuCodeSet(Set<String> roleCodeSet) {
        return null;
    }

    /**
     * 通过ID获取其子一级菜单
     *
     * @param parentId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Menu> listSubMenuByParentId(String parentId) throws Exception {
        return (List<Menu>) baseDao.findForList("MenuMapper.listSubMenuByParentId", parentId);
    }

    /**
     * 通过菜单ID获取数据
     *
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData getMenuById(PageData pd) throws Exception {
        return (PageData) baseDao.findForObject("MenuMapper.getMenuById", pd);
    }

    public Menu getMenuByIds(PageData pd) throws Exception {
        return (Menu) baseDao.findForObject("MenuMapper.getMenuByIds", pd);
    }


    /**
     * 新增菜单
     *
     * @param menu
     * @throws Exception
     */
    public Object saveMenu(Menu menu) throws Exception {
        return baseDao.save("MenuMapper.saveMenu", menu);
    }

    /**
     * 取最大ID
     *
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData findMaxId(PageData pd) throws Exception {
        return (PageData) baseDao.findForObject("MenuMapper.findMaxId", pd);
    }

    /**
     * 删除菜单
     *
     * @param pd
     * @throws Exception
     */
    public Boolean deleteMenuById(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.delete("MenuMapper.deleteMenuId", pd);
        return (result > 0) ? true : false;
    }

    /**
     * 编辑
     *
     * @param menu
     * @return
     * @throws Exception
     */
    public Boolean updateMenu(Menu menu) throws Exception {
        Integer result = (Integer) baseDao.update("MenuMapper.updateMenu", menu);
        return (result > 0) ? true : false;
    }

    /**
     * 编辑
     *
     * @param pd
     * @return
     * @throws Exception
     */
    public Boolean updateMenuPd(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.update("MenuMapper.updateMenuPd", pd);
        return (result > 0) ? true : false;
    }

    /**
     * 保存菜单图标
     *
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData editicon(PageData pd) throws Exception {
        return (PageData) baseDao.findForObject("MenuMapper.editicon", pd);
    }

    /**
     * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
     *
     * @param MENU_ID
     * @return
     * @throws Exception
     */
    public List<Menu> listAllMenu(String MENU_ID) throws Exception {
        List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
        for (Menu menu : menuList) {
            menu.setMenuUrl("menu/toEdit.do?MENU_ID=" + menu.getMenuId());
            menu.setSubMenu(this.listAllMenu(menu.getMenuId() + ""));
        }
        return menuList;
    }

    /**
     * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
     *
     * @param MENU_ID
     * @return
     * @throws Exception
     */
    public List<Menu> listAllMenuQx(String MENU_ID) throws Exception {
        List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
        for (Menu menu : menuList) {
            menu.setSubMenu(this.listAllMenuQx(menu.getMenuId() + ""));
        }
        return menuList;
    }

    public List<Menu> listAllMenuByUser(String userId) throws Exception {
        return null;
    }

    @Cacheable(cacheNames = "menuCache")
    public List<Menu> listAllMenu() throws Exception {
        return (List<Menu>) baseDao.findForList("MenuMapper.listAllMenu", null);
    }

    @Override
    public List<TreeNode> selectMenuCascade(PageData pd) throws Exception {
        return (List<TreeNode>) baseDao.findForList("MenuMapper.selectMenuCascade", pd);
    }

    @Override
    public Menu getSupMenu(int supId) throws Exception {
        return (Menu) baseDao.findForObject("MenuMapper.getBySupId", supId);
    }
}
