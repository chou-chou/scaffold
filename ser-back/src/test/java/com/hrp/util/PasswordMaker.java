package com.hrp.util;

import com.hrp.utils.Constant;
import com.hrp.utils.EncryptUtil;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * PasswordMaker
 *
 * @author KVLT
 * @date 2017-04-24.
 */
public class PasswordMaker {

    @Test
    public void password() {
        String algorithmName = "md5";
        String salt = Constant.salt;
        String _salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 2;

        String username = "kevin";
        String password = "kevin";

        // 散列算法： md5(md5(密码+username+salt))
        //String pwd = new Md5Hash(password, username + salt, hashIterations).toHex();
        SimpleHash hash = new SimpleHash(algorithmName, password, username + salt, hashIterations);
        String encodedPwd = hash.toHex();

        System.out.println(encodedPwd);
    }

    @Test
    public void md5Password() {
        String username = "admin";
        String password = "admin";

        String s = EncryptUtil.md5Password(username, password);
        System.out.println(s);
    }
}
