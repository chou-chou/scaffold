package com.hrp.service;

import com.hrp.entity.system.Menu;
import com.hrp.entity.system.TreeNode;
import com.hrp.utils.PageData;

import java.util.List;
import java.util.Set;

/**
 * MenuService
 *
 * @author KVLT
 * @date 2017-03-24.
 */
public interface MenuService {

    /**
     * 获取角色code对应的菜单权限的并集
     * @param roleCodeSet
     * @return
     */
    public Set<String> getMenuCodeSet(Set<String> roleCodeSet);

    /**
     * 通过ID获取其子一级菜单
     * @param menuId
     * @return
     */
    public List<Menu> listSubMenuByParentId(String menuId) throws Exception;

    /**
     * 通过菜单ID获取数据
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData getMenuById(PageData pd) throws Exception;

    public Menu getMenuByIds(PageData pd) throws Exception;

    /**
     * 新增菜单
     * @param menu
     * @throws Exception
     */
    public Object saveMenu(Menu menu) throws Exception;

    /**
     * 取最大ID
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData findMaxId(PageData pd) throws Exception;

    /**
     * 删除菜单
     * @param pd
     * @throws Exception
     */
    public Boolean deleteMenuById(PageData pd) throws Exception;

    /**
     * 编辑
     * @param menu
     * @return
     * @throws Exception
     */
    public Boolean updateMenu(Menu menu) throws Exception;

    public Boolean updateMenuPd(PageData pd) throws Exception;

    /**
     * 保存菜单图标
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData editicon(PageData pd) throws Exception;

    /**
     * 获取所有菜单项
     * @return
     * @throws Exception
     */
    public List<Menu> listAllMenu() throws Exception;

    /**
     * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
     * @param MENU_ID
     * @return
     * @throws Exception
     */
    public List<Menu> listAllMenu(String MENU_ID) throws Exception;

    /**
     * 获取当前用户可见的菜单选项（用户 —— 角色 —— 菜单）
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Menu> listAllMenuByUser(String userId) throws Exception;

    /**
     * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
     * @param MENU_ID
     * @return
     * @throws Exception
     */
    public List<Menu> listAllMenuQx(String MENU_ID) throws Exception;

    /**
     * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)  返回给zTree处理
     * @param pd
     * @return
     * @throws Exception
     */
    public List<TreeNode> selectMenuCascade(PageData pd) throws Exception;

    public Menu getSupMenu(int supId)throws Exception;

}
