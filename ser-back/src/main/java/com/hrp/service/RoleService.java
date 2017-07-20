package com.hrp.service;

import com.hrp.entity.system.Button;
import com.hrp.entity.system.Role;
import com.hrp.utils.PageData;

import java.util.List;

/**
 * RoleService
 * 角色接口类
 * @author KVLT
 * @date 2017-03-23.
 */
public interface RoleService {

    /**
     * 列出次组下级角色
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Role> listAllRolesByPId(PageData pd) throws Exception;

    /**
     * 通过id查找
     * @param pd
     * @return
     */
    public PageData findObjectById(PageData pd) throws Exception;

    /**
     * 添加角色
     * @param pd
     * @throws Exception
     */
    public void add(PageData pd) throws Exception;

    public Object add(Role role) throws Exception;
    /**
     * 保存修改
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception;

    /**
     * 删除角色
     * @param ROLE_ID
     * @throws Exception
     */
    public boolean deleteRoleById(int[] ROLE_ID) throws Exception;

    /**
     * 给当前角色附加按钮权限
     * @param role
     * @throws Exception
     */
    public void updateRoleButtons(Role role) throws Exception;

    /**
     * 给当前角色附加菜单资源权限
     * @param role
     * @throws Exception
     */


    public void updateRoleMenus(Role role) throws Exception;

    /**
     * 通过id查找
     * @param ROLE_ID
     * @return
     * @throws Exception
     */
    public Role getRoleById(String ROLE_ID) throws Exception;

    public Role getByRoleId(PageData pd) throws Exception ;

    public boolean updateRole(PageData pd) throws Exception;

    public List<Button> getButtonByRoleId(Integer roleId) throws Exception;

    public boolean batchSaveRoleBtn(Integer roleId, Integer[] btnIds) throws Exception;

    public boolean batchDeleteRoleBtn(Integer roleId, Integer[] btnIds) throws Exception;

}
