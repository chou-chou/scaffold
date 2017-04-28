package com.hrp.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;
import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * BaseShiroTest
 * 集成shiro的测试基类
 * @author KVLT
 * @date 2017-04-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-test-base.xml"})
@ActiveProfiles("dev")
public class BaseShiroTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final transient Logger logger = LoggerFactory.getLogger(BaseShiroTest.class);

    private static ThreadState subjectThreadState;

    @Autowired
    private DefaultSecurityManager securityManager;

    protected void login(String userName, String password) {
        logger.info("userName can not be empty or null.", !StringUtils.isAnyEmpty(userName));
        logger.info("password can not be empty or null.", !StringUtils.isAnyEmpty(password));
        setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(userName, password));
    }

    private void setSubject(Subject subject){
        doClearSubject();
        createThreadState(subject).bind();
    }

    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    private ThreadState createThreadState(Subject subject) {
        return new SubjectThreadState(subject);
    }

    private static void setSecurityManager(SecurityManager securityManager) {
        SecurityUtils.setSecurityManager(securityManager);
    }

    private SecurityManager getSecurityManager() {
        return SecurityUtils.getSecurityManager();
    }

    private void doClearSubject() {
        if (subjectThreadState != null) {
            subjectThreadState.clear();
            subjectThreadState = null;
        }
    }

    @After
    public void tearDown() {
        SecurityUtils.getSubject().logout();
    }
}
