package com.hrp.dao;

import com.hrp.entity.system.User;
import com.hrp.utils.PageData;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

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

        pd.put("username", "admin");
        pd.put("password", "0b018efd04c9eed97e04d18e3baee171");

        User user = (User) dao.findForObject("UserMapper.getUserInfo", pd);

        if (user != null) logger.info(user.toString());
    }

    @Test
    @Rollback(false)
    public void saveUser() throws Exception {

        for (int i=0; i< 100; i++) {
            User user = new User();
            user.setUserName("kv" + i%2 + "in" + i/2);
            user.setPassword("0c030b060c0d050c0c0903020109010901030f0a");
            user.setAccount("kv" + i%2 + "in" + i);
            user.setEnabled(i%2 == 1);
            user.setLastLogin(com.hrp.utils.lang.DateUtil.formatDate(new Date()));
            user.setExpired(i%2 == 1);
            user.setLocked(i%2 == 1);

            Object obj = dao.save("UserMapper.saveUser", user);
            logger.info("    SQL影响行数 ：" + obj + "\t 新增ID ： " + user.getUserId());
        }
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
    @Test
    public void inserUser() throws Exception {

        User user = new User();
        user.setUserName("yueliang");
        user.setPassword("123456");
        user.setAccount("592774130" );
        user.setEnabled(true);
        user.setLastLogin("2017-03-29 12:53:08");
        user.setExpired(false);
        user.setLocked(false);

        dao.findForObject("UserMapper.saveUser", user);

        if (null != user)
        System.out.println(user.getUserId());
    }

}
