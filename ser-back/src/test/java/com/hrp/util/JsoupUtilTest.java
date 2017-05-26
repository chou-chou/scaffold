package com.hrp.util;

import com.hrp.utils.Constant;
import com.hrp.utils.JsoupUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JsoupUtilTest
 *
 * @author KVLT
 * @date 2017-05-19.
 */
public class JsoupUtilTest {

    protected final static Logger logger = LoggerFactory.getLogger(JsoupUtilTest.class);

    @Test
    public void parse() throws Exception {
        String filePath = "E:\\WorkSpaces\\IdeaWpaces\\SER\\ser-back\\target\\ser\\WEB-INF\\views\\back\\system\\department\\department_main.jsp";
        JsoupUtil ju = JsoupUtil.newInstance();
        String[] eles = new String[]{
                "button.inCtrl",
                "." + Constant.IN_CONTROL,
                "input[" + Constant.IN_CONTROL + "]"
        };
        ju.parseBtnByFile(filePath, ".inCtrl");
    }
}
