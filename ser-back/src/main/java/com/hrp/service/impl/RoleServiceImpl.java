package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.Role;
import com.hrp.service.RoleService;
import com.hrp.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        return null;
    }

    public PageData findObjectById(PageData pd) throws Exception {
        return null;
    }

    public void add(PageData pd) throws Exception {

    }

    public void edit(PageData pd) throws Exception {

    }

    public void deleteRoleById(String ROLE_ID) throws Exception {

    }

    public void updateRoleButtons(Role role) throws Exception {

    }

    public void updateRoleMenus(Role role) throws Exception {

    }

    public Role getRoleById(String ROLE_ID) throws Exception {
        return null;
    }
}
