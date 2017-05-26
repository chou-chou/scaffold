package com.hrp.controller;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.hrp.dao.BaseDao;
import com.hrp.dao.BaseDaoTest;
import com.hrp.utils.PageData;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * BaseMvcTest
 *
 * @author KVLT
 * @date 2017-05-18.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-test-base.xml", "classpath:spring/spring-mvc.xml"})
public class BaseMvcTest extends AbstractJUnit4SpringContextTests {

    protected static final Logger logger = LoggerFactory.getLogger(BaseMvcTest.class);

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mvc;

    protected static PageData pd = new PageData();

    static {
        LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        try {
            System.out.println("日志配置文件logback.xml路径： "  + BaseDaoTest.class.getClassLoader().getResource("config/logback.xml"));

            configurator.doConfigure(BaseDaoTest.class.getClassLoader().getResource("config/logback.xml"));
        } catch (JoranException e) {
            e.printStackTrace();
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
        System.out.println("===================");
        logger.debug("Hello Logback initial - {}","debug message");
    }

    @Resource(name = "baseDao")
    protected BaseDao dao;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(wac).build();
    }

    public <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    protected ApplicationContext getContext() {
        return applicationContext;
    }

}
