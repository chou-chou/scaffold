package com.hrp.service;

import com.hrp.entity.system.User;
import com.hrp.utils.PageData;
import com.hrp.utils.plugins.Page;

import java.util.List;
import java.util.Set;

/**
 * UserServiceImpl
 * 用户接口类
 * @author KVLT
 * @date 2017-03-15.
 */
public interface UserService {

    public void login(User user);

    public void logout();

    /**
     * 登录判断
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public User getUserByNameAndPwd(String username, String password) throws Exception;

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     * @throws Exception
     */
    public User getUserByLoginName(String username)throws Exception;

    /**
     * 更新登录时间
     * @param pd
     * @throws Exception
     */
    public void updateLastLogin(PageData pd)throws Exception;

    /**
     * 通过用户ID获取用户信息和角色信息
     * @param userId
     * @return
     * @throws Exception
     */
    public User getUserRoleById(String userId) throws Exception;

    /**通过USERNAEME获取数据
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData findByUsername(PageData pd) throws Exception;

    /**
     * 列出某角色下的所有用户
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> listAllUserByRoldId(PageData pd)throws Exception;

    /**
     * 用户列表
     * @param page
     * @return
     * @throws Exception
     */
    public List<PageData> listPdPageUser(Page page) throws Exception;

    /**
     * 用户列表
     * @param page
     * @return
     * @throws Exception
     */
    public List<PageData> listUsers(Page page) throws Exception;

    /**
     * 通过id获取数据
     * @param pd
     * @return
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception;

    /**
     * 修改用户
     * @param pd
     * @throws Exception
     */
    public void editUser(PageData pd)throws Exception;

    /**
     * 保存用户
     * @param pd
     * @throws Exception
     */
    public void saveU(PageData pd)throws Exception;

    /**
     * 删除用户
     * @param pd
     * @throws Exception
     */
    public void deleteU(PageData pd)throws Exception;

    /**
     * 批量删除用户
     * @param USER_IDS
     * @throws Exception
     */
    public boolean deleteAllU(String[] USER_IDS)throws Exception;

    /**
     * 用户列表(全部)
     * @param pd
     * @return
     * @throws Exception
     */
    public List<PageData> listAllUser(PageData pd)throws Exception;

    /**
     * 获取总数
     * @param value
     * @throws Exception
     */
    public PageData getUserCount(String value) throws Exception;

    public Set<String> findAllRoleNamesByUsername(String username);
    public Set<String> findAllPermissionsByUsername(String name);

    void changePassword(String userId, String newPassword);

    public void clearAllUserAndRoleConnection(String userId);

    public void connectUserAndRole(String userId, Set<Integer> roleIds);
    public void desconnectUserAndRole(String userId, Set<Integer> roleIds);

    public void update(User user);
    //新增用户
    public Object addUser(User user) throws Exception;

    //更新用户
    public boolean updateUser(PageData pd) throws Exception;


    public User getByUserId(PageData pd) throws Exception;



}
