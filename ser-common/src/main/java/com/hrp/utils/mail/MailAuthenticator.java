package com.hrp.utils.mail;

import javax.mail.*;

/**
 * MailAuthenticator
 * 发送邮件需要使用的基本信息[邮箱登陆验证]
 * @author KVLT
 * @date 2017-03-14.
 */
public class MailAuthenticator extends Authenticator {
    /**
     * 用户名（登陆邮箱）
     */
    String userName=null;
    /**
     * 密码
     */
    String password=null;

    public MailAuthenticator(){
    }
    public MailAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
