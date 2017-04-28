package com.hrp.dao;

import com.hrp.entity.system.User;
import com.hrp.utils.PageData;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UserMapperTest
 *
 * @author KVLT
 * @date 2017-03-15.
 */
public class UserMapperTest extends BaseDaoTest {

    @Test
    @SuppressWarnings("unchecked")
    public void getUserInfo() throws Exception {
        PageData pd = new PageData();

        pd.put("ACCOUNT", "admin");
        pd.put("PASSWORD", "admin");

        List<PageData> pdList = (ArrayList<PageData>) dao.findForList("UserMapper.getUserInfo", pd);

        for (PageData p : pdList){
            p.print();
        }
    }

    @Test
    @Rollback(false)
    public void saveUser() throws Exception {
        User user = new User();
        user.setUsername("kevin");
        user.setPassword("0c030b060c0d050c0c0903020109010901030f0a");
        user.setAccount("kevin");
        user.setEnabled(true);
        user.setLastLogin(com.hrp.utils.lang.DateUtil.formatDate(new Date()));
        user.setExpired(true);
        user.setLocked(true);

        Object obj = dao.save("UserMapper.saveUser", user);
        logger.info("    SQL影响行数 ：" + obj + "\t 新增ID ： " + user.getUserId());
    }

    @Test
    @Rollback(false)
    public void updateUser() throws Exception {
        User user = new User();
        user.setUserId("0AEFD3A66FDA42C49531C0263BDF5C6E");
        user = (User)dao.findForObject("UserMapper.getByUserId", user);

        logger.info(user.toString());

        user.setLastLogin(com.hrp.utils.lang.DateUtil.formatDate(new Date()));

        Object obj = dao.update("UserMapper.updateUser", user);
        logger.info(" 更新的行数: " + obj + " \n" + user.toString());
    }

    @Test
    public void listAllUser() {

    }

    @Test
    public void findByAccount() throws Exception {
        PageData pd = new PageData();
        pd.put("ACCOUNT", "admin");

        User user = (User) dao.findForObject("UserMapper.getByAccount", pd);
        logger.info(user.getUserId());
    }
}
