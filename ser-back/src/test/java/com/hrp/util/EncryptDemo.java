package com.hrp.util;

import com.hrp.utils.EncryptUtil;
import org.junit.Test;

/**
 * EncryptDemo
 *
 * @author KVLT
 * @date 2017-04-18.
 */
public class EncryptDemo extends BaseDemo {

    @Test
    public void encrypt() {
        String pwd = EncryptUtil.getPassword("kevin", "kevin");
        logger.info(pwd);
    }
}
