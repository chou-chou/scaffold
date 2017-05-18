package com.hrp.controller;

import com.hrp.utils.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * IndexController
 *
 * @author KVLT
 * @date 2017-05-17.
 */
@Controller
public class FirstController {

    /**
     * 直接跳转到后台登录页面
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String root() {
        return Constant.BACK_LOGIN_PAGE;
    }

    /**
     * 直接跳转到后台登录页面
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String _root() {
        return Constant.BACK_LOGIN_PAGE;
    }

    /**
     * 成功登录之后，会跳转到这里
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return Constant.INDEX_PAGE;
    }
}
