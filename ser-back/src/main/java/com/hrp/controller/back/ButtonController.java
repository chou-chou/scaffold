package com.hrp.controller.back;

import com.hrp.controller.common.BaseController;
import com.hrp.service.ButtonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * ButtonController
 * 按钮模块（这里的按钮对应菜单界面的按钮）
 * @author KVLT
 * @date 2017-05-16.
 */
@Controller
@RequestMapping("/b/button/")
public class ButtonController extends BaseController {

    // Button模块基础路径
    private final static String BASE_PATH = "back/system/button/";  // --> WEB-INF/views/system/button/

    @Resource
    private ButtonService buttonService;

}
