package com.hrp.controller;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * SystemControllerTest
 *
 * @author KVLT
 * @date 2017-05-18.
 */
public class SystemControllerTest extends BaseMvcTest {

    @Test
    public void mappingList() throws Exception {
        MvcResult mr = mvc.perform(MockMvcRequestBuilders.get("/c/system/mappingList.do"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MockHttpServletResponse response = mr.getResponse();
        String json = response.getContentAsString();
        logger.info(json);
    }
}
