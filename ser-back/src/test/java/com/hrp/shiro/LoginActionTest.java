package com.hrp.shiro;

import com.hrp.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * LoginActionTest
 * 登录测试类
 * @author KVLT
 * @date 2017-04-24.
 */
public class LoginActionTest extends BaseShiroTest {

    @Autowired
    private UserService userService;

    @Test
    public void login() {
        login("kevin", "0c030b060c0d050c0c0903020109010901030f0a");
    }
}
