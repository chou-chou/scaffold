package com.hrp.util;

import com.hrp.dao.BaseDao;
import jodd.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * BaseDemo
 *
 * @author KVLT
 * @date 2017-03-31.
 */
public class BaseDemo {

    protected final static Logger logger = LoggerFactory.getLogger(BaseDao.class);

    public static void main(String[] args) throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey deskey = keygen.generateKey();
        System.out.println(Base64.encodeToString(deskey.getEncoded()));

    }
}
