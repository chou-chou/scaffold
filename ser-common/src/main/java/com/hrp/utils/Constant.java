package com.hrp.utils;

import com.hrp.pojo.ResultCode;
import org.springframework.context.ApplicationContext;

/**
 * Constant
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public class Constant {

    public final static String INDEX_PAGE = "index";
    public final static String MAIN_PAGE = "redirect:/index.do";
    public final static String LOGIN_PAGE = "login";
    public final static String REGISTER_PAGE = "register";
    public final static String LOCK_PAGE = "locked";
    public final static String AUTHORITY_PAGE = "authorization";

    public final static String S_REMEMBER_ME = "rememberMe";  // 记住我
    public final static String S_ADMIN = "admin";  //

    public final static String SHIRO_LOGIN_FAILURE = "shiroLoginFailure";  // shiro错误类名

    /**
     * 批量操作时，每批操作的数据单位个数
     */
    public final static int BATCH_UNIT_COUNT = 200;

    /**
     * 项目是否可用
     */
    public static final String SYS_ENABLED = "sys_enabled";

    /**
     * 项目名称（中文，页面输出）
     */
    public static final String SYSTEM_NAME = "科教研管理系统";

    /**
     * 默认验证码参数名称
     */
    public static final String DEFAULT_CAPTCHA_PARAM = "captchaCode";
    /**
     * 消息key
     */
    public static final String MESSAGE = "message";

    /**
     * 错误key
     */
    public static final String ERROR = "error";
    /**
     * 当前登录的用户
     */
    public static final String CURRENT_USER = "currentUser";
    public static final String CURRENT_USERNAME = "currentUserName";

    /**
     * 并发登录控制
     */
    public static final String KICKOUT_STATUS = "kickout_status";
    public static final String KICKOUT = "kickout";

    // 默认编码
    public static final String DEFAULT_ENCODING = "utf-8";
    public static final String SESSION_USER = "sessionUser";			//session用的用户
    public static final String SESSION_allmenuList = "allmenuList";		//全部菜单
    public static final String LOGIN = "/login.do";				//登录地址

    public static final String SESSION_USERROL = "USERROL";				//用户对象
    public static final String SESSION_USERNAME = "USERNAME";			//用户名
    public static final String SESSION_QX = "QX";

    public static final String PAGE	= "admin/config/PAGE.txt";			//分页条数配置路径
    public static final String SMS1 = "admin/config/SMS1.txt";			//短信账户配置路径1
    public static final String SMS2 = "admin/config/SMS2.txt";			//短信账户配置路径2
    public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";//WEBSOCKET配置路径

    public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化

    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)).*";	//不对匹配该值的访问路径拦截（正则）

    public static final ResultCode CSRF_ERROR = new ResultCode("101", "CSRF ERROR:无效的token，或者token过期");

    public static final int DELETE_CRITICAL_VAL = 50;  // 批量删除时指定的单批次删除记录数量

    /**
     * session status
     */
    public static final String SESSION_STATUS ="ser-online-status";
    public static final int PASSWORD_RETRY_MAX = 10;//密码重试次数
    public static final String salt = "tbyf!@#$%";  // 加盐

}
