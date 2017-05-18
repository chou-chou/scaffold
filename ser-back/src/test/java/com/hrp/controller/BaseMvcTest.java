package com.hrp.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * BaseMvcTest
 *
 * @author KVLT
 * @date 2017-05-18.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-test-base.xml", "classpath:spring/spring-mvc.xml"})
public class BaseMvcTest extends AbstractTransactionalJUnit4SpringContextTests {

    protected static final Logger logger = LoggerFactory.getLogger(BaseMvcTest.class);

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(wac).build();
    }

}
