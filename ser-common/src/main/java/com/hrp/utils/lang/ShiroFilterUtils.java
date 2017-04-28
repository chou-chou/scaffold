package com.hrp.utils.lang;

import net.sf.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Map;

/**
 * ShiroFilterUtils
 *
 * @author KVLT
 * @date 2017-03-28.
 */
public class ShiroFilterUtils {

    final static Class<? extends ShiroFilterUtils> CLAZZ = ShiroFilterUtils.class;

    public static final int HTTP_STATUS_SESSION_EXPIRE = 403;

    //登录页面
    static final String LOGIN_URL = "/login.shtml";
    //踢出登录提示
    final static String KICKED_OUT = "/login?kickout=1";
    //没有权限提醒
    final static String UNAUTHORIZED = "/unauthorized.do";
    /**
     * 是否是Ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request){
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

    /**
     * response 输出JSON
     * @param response
     * @param resultMap
     */
    public static void out(ServletResponse response, Map<String, String> resultMap){

        PrintWriter out = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            out = response.getWriter();
            out.println(JSONObject.fromObject(resultMap).toString());
        } catch (Exception e) {
            LoggerUtils.error("输出JSON报错。");
        }finally{
            if(null != out){
                out.flush();
                out.close();
            }
        }
    }
}
