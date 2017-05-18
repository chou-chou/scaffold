package com.hrp.controller.common;

import com.hrp.utils.Constant;
import com.hrp.utils.io.VerifyCodeUtils;
import com.hrp.utils.lang.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CommonController
 * 公共Controller
 * @author KVLT
 * @date 2017-05-17.
 */
@Controller
@RequestMapping("/c/")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping(value = "anon/locked")
    public String locked() {
        return Constant.LOCK_PAGE;
    }

    /**
     * 跳转到系统认证失败页面
     * @return
     */
    @RequestMapping(value = "anon/authorization.do")
    public String authorization() {
        logger.info("跳转到authorization.jsp页面 ... ");
        return Constant.AUTHORITY_PAGE;
    }

    /**
     * 403错误
     * @return
     */
    @RequestMapping("anon/403.do")
    public ModelAndView _403(){
        ModelAndView view = new ModelAndView("error/403");
        return view;
    }

    /**
     * 404错误
     * @return
     */
    @RequestMapping("anon/404.do")
    public ModelAndView _404(){
        ModelAndView view = new ModelAndView("error/404");
        return view;
    }

    /**
     * 500错误
     * @param request
     * @return
     */
    @RequestMapping("anon/500.do")
    public ModelAndView _500(HttpServletRequest request){
        ModelAndView view = new ModelAndView("error/500");

        Throwable t = (Throwable)request.getAttribute("javax.servlet.error.exception");
        String defaultMessage = "未知" ;
        if(null == t){
            view.addObject("line", defaultMessage);
            view.addObject("clazz", defaultMessage);
            view.addObject("methodName",defaultMessage);
            return view;
        }
        String message = t.getMessage() ;//错误信息
        StackTraceElement[] stack = t.getStackTrace();
        view.addObject("message", message);
        if(null != stack && stack.length != 0 ){
            StackTraceElement element = stack[0];
            int line = element.getLineNumber();//错误行号
            String clazz = element.getClassName();//错误java类
            String fileName = element.getFileName();

            String methodName = element.getMethodName() ;//错误方法
            view.addObject("line", line);
            view.addObject("clazz", clazz);
            view.addObject("methodName",methodName);
        }
        return view;
    }

    /**
     * 获取验证码
     * @param response
     */
    @RequestMapping(value="anon/getVCode.do", method= RequestMethod.GET)
    public void getVCode(HttpServletResponse response, HttpServletRequest request){
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");

            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            //存入Shiro会话session
            //tokenManager.setVal2Session(VerifyCodeUtils.V_CODE, verifyCode.toLowerCase());
            //生成图片
            int w = 146, h = 33;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (Exception e) {
            LoggerUtils.error("获取验证码异常：%s", e);
        }
    }

}
