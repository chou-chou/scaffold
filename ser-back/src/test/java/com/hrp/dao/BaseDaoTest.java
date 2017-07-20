package com.hrp.dao;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.hrp.utils.PageData;
import com.hrp.utils.plugins.Page;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * BaseDaoTest
 *
 * @author KVLT
 * @date 2017-03-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:mybatis/mybatis-config.xml", "classpath:spring/spring-mybatis.xml"})
@Transactional(transactionManager = "transactionManager")
public class BaseDaoTest extends AbstractTransactionalJUnit4SpringContextTests /*AbstractJUnit4SpringContextTests*/ {
    /**
     * AbstractTransactionalJUnit4SpringContextTests默认会回滚。
     * 在@Test标签附近增加标签@Rollback(false)  -- > 增加/删除/修改
     */

    protected static final Logger logger = LoggerFactory.getLogger(BaseDaoTest.class);

    protected static PageData pd = new PageData();
    protected static Page page = new Page();

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
    public BaseDao dao;

    public static void main(String[] args) {
        System.out.println("###");
    }

}
