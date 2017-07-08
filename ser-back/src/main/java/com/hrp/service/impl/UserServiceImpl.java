package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.*;
import com.hrp.service.UserService;
import com.hrp.utils.PageData;
import com.hrp.utils.lang.StringUtil;
import com.hrp.utils.plugins.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * UserServiceImpl
 *
 * @author KVLT
 * @date 2017-03-15.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "baseDao")
    private BaseDao baseDao;

    @Override
    public void login(User user) {

    }

    @Override
    public void logout() {
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
    }

    public User getUserByNameAndPwd(String username, String password) throws Exception {
        PageData pd = new PageData();
        pd.put("username", username);
        pd.put("password", password);

        return (User) baseDao.findForObject("UserMapper.getUserInfo", pd);
    }

    public User getUserByLoginName(String account) throws Exception {
        PageData pd = new PageData();
        pd.put("ACCOUNT", account);

        return (User) baseDao.findForObject("UserMapper.getByAccount", pd);
    }

    public void updateLastLogin(PageData pd) throws Exception {
        baseDao.update("UserMapper.updateLastLogin", pd);
    }

    public User getUserRoleById(String userId) throws Exception {
        return (User) baseDao.findForObject("UserMapper.getUserRoleById", userId);
    }

    public PageData findByUsername(PageData pd) throws Exception {
        return (PageData) baseDao.findForObject("UserMapper.findByUsername", pd);
    }

    public List<PageData> listAllUserByRoldId(PageData pd) throws Exception {
        return (List<PageData>) baseDao.findForList("UserMapper.listAllUserRoleId", pd);
    }

    public List<PageData> listUsers(Page page) throws Exception {
        return null;
    }

    public PageData findById(PageData pd) throws Exception {
        return null;
    }

    public void editUser(PageData pd) throws Exception {

    }

    public void saveU(PageData pd) throws Exception {

    }

    public void deleteU(PageData pd) throws Exception {

    }

    public boolean deleteAllU(String[] USER_IDS) throws Exception {
        Integer result = (Integer) baseDao.delete("UserMapper.batchDeleteUser", USER_IDS);
        return (result > 0) ? true : false;
    }

    public List<PageData> listAllUser(PageData pd) throws Exception {
        return null;
    }

    public PageData getUserCount(String value) throws Exception {
        return null;
    }

    public Object save(String str, Object obj) throws Exception {
        return null;
    }

    public Object update(String str, Object obj) throws Exception {
        return null;
    }

    public Object delete(String str, Object obj) throws Exception {
        return null;
    }

    public Object findForObject(String str, Object obj) throws Exception {
        return null;
    }

    public Object findForList(String str, Object obj) throws Exception {
        return null;
    }

    public Object findForMap(String sql, Object obj, String key) throws Exception {
        return null;
    }

    @Override
    public Set<String> findAllRoleNamesByUsername(String username) {
        Set<String> set = new HashSet<String>();
        set.add("admin");
        return set;
    }

    @Override
    public Set<String> findAllPermissionsByUsername(String name) {
        return null;
    }

    @Override
    public void changePassword(String userId, String newPassword) {

    }

    @Override
    public void clearAllUserAndRoleConnection(String userId) {

    }

    @Override
    public void connectUserAndRole(String userId, Set<Integer> roleIds) {

    }

    @Override
    public void desconnectUserAndRole(String userId, Set<Integer> roleIds) {

    }

    @Override
    public void update(User user) {

    }

    public Object addUser(User user) throws Exception {
        return baseDao.save("UserMapper.saveUser", user);
    }

    @Override
    public boolean updateUser(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.update("UserMapper.updateUser", pd);
        return (result > 0) ? true : false;

    }

    /**
     * 用户列表
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> listPdPageUser(Page page) throws Exception {
        return (List<PageData>) baseDao.findForList("UserMapper.userListPage", page);
    }

    @Override
    public User getByUserId(PageData pd) throws Exception {
        return (User) baseDao.findForObject("UserMapper.getByUserId", pd);
    }

    @Override
    public List<UserRoleLink> findRoleIdByUserId(PageData pd) throws Exception {
        return (List<UserRoleLink>) baseDao.findForList("UserMapper.findRoleIdByUserId", pd);
    }
    @Override
    public void deleteUserRole(String USER_ID) throws Exception {
        baseDao.delete("UserMapper.deleteUserRole", USER_ID);
    }

    @Override
    public Object insertUserRole(List<UserRoleLink> userRoleLink) throws Exception {
        return baseDao.batchSave("UserMapper.insertUserRole", userRoleLink);
    }

    @Override
    @Cacheable(value = "privilegeCache", key = "'b_' + #userId")  // 根据userId缓存对应的button信息
    public List<Button> getButtonByUserId(String userId) throws Exception {
        if (StringUtil.isBlankOrNull(userId)) {
            return null;
        }

        return (List<Button>) baseDao.findForList("ButtonMapper.getButtonByUserId", userId);
    }

    @Override
    @Cacheable(value = "privilegeCache", key = "'m_' + #userId")  // 根据userId缓存对应的menu信息
    public List<Menu> getMenuByUserId(String userId) throws Exception {
        if (StringUtil.isBlankOrNull(userId)) {
            return null;
        }

        return (List<Menu>) baseDao.findForList("MenuMapper.getMenuByUserId", userId);
    }

    @Override
    @Cacheable(value = "privilegeCache", key = "'m_t_' + #userId")
    public List<TreeNode> getMenuTreeByUserId(String userId) throws Exception {
        if (StringUtil.isBlankOrNull(userId)) {
            return null;
        }

        return (List<TreeNode>) baseDao.findForList("MenuMapper.getMenuTreeByUserId", userId);
    }
}
