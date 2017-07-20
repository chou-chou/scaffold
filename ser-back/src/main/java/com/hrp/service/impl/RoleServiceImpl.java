package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.Button;
import com.hrp.entity.system.Role;
import com.hrp.service.RoleService;
import com.hrp.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * RoleServiceImpl
 *
 * @author KVLT
 * @date 2017-03-23.
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    public BaseDao baseDao;

    public Object save(String str, Object obj) throws Exception {
        return null;
    }

    public Object update(String str, Object obj) throws Exception {
        return null;
    }

    public Object delete(String str, Object obj) throws Exception {
        return null;
    }

    public Set<String> getRoleCodeSet(String userName) {
        PageData pd = new PageData();
        try {
            baseDao.findForList("", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Role> listAllRolesByPId(PageData pd) throws Exception {
        return( List<Role>)baseDao.findForList("RoleMapper.listAllRole",pd);
    }

    public PageData findObjectById(PageData pd) throws Exception {
        return null;
    }

    public void add(PageData pd) throws Exception {

    }

    public Object add(Role role) throws Exception {
        return baseDao.save("RoleMapper.saveRoleBean",role);
    }
    public void edit(PageData pd) throws Exception {

    }

    public boolean deleteRoleById(int[] roleIds) throws Exception {
        Integer result = (Integer) baseDao.delete("RoleMapper.batchDeleteRole", roleIds);
        return (result > 0) ? true : false;
    }

    public void updateRoleButtons(Role pd) throws Exception {

    }



    public void updateRoleMenus(Role role) throws Exception {

    }

    public Role getRoleById(String ROLE_ID) throws Exception {
        return null;
    }

    @Override
    public Role getByRoleId(PageData pd) throws Exception {
        return (Role) baseDao.findForObject("RoleMapper.getByRoleId", pd);
    }
    @Override
    public boolean updateRole(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.update("RoleMapper.updateRole", pd);
        return (result > 0) ? true : false;
    }

    @Override
    public List<Button> getButtonByRoleId(Integer roleId) throws Exception {
        return (List<Button>) baseDao.findForList("ButtonMapper.getButtonByRoleId", roleId);
    }

    @Override
    public boolean batchSaveRoleBtn(Integer roleId, Integer[] btnIds) throws Exception {
        if (null == roleId || null == btnIds || btnIds.length <= 0) {
            return false;
        }

        List<PageData> pdList = new ArrayList<>();

        for (Integer btnId : btnIds) {
            PageData pd = new PageData();
            pd.put("ROLE_ID", roleId);
            pd.put("BTN_ID", btnId);

            pdList.add(pd);
        }

        Integer result = (Integer) baseDao.save("RoleMapper.batchSaveRoleBtn", pdList);
        return result > 0 ? true : false;
    }

    @Override
    public boolean batchDeleteRoleBtn(Integer roleId, Integer[] btnIds) throws Exception {
        if (null == roleId || null == btnIds || btnIds.length <= 0) {
            return false;
        }

        PageData pd = new PageData();
        pd.put("ROLE_ID", roleId);
        pd.put("array", btnIds);

        Integer result = (Integer) baseDao.delete("RoleMapper.batchDeleteRoleBtn", pd);
        return result > 0 ? true : false;
    }
}
