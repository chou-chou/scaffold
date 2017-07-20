package com.hrp.controller.back;

import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.Button;
import com.hrp.entity.system.TreeNode;
import com.hrp.entity.system.User;
import com.hrp.pojo.Result;
import com.hrp.service.ButtonService;
import com.hrp.service.UserService;
import com.hrp.utils.Constant;
import com.hrp.utils.JsonUtil;
import com.hrp.utils.lang.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private UserService userSerivce;

    /**
     * 菜单主页面
     */
    @RequestMapping(method = RequestMethod.GET, value = "/main.do")
    @MvcMapping(tag = "button:main", path = BASE_PATH + "button_main", type = MvcMapping.ViewType.JSP)
    private ModelAndView main(HttpSession session) {
        ModelAndView mv = this.getModelAndView(BASE_PATH + "button_main");
        List<TreeNode> nodeList = new ArrayList();
        List<Button> allBtnList = new ArrayList();
        List<Button> ownBtnList = new ArrayList();

        User user = (User)session.getAttribute(Constant.CURRENT_USER);  // 当前用户
        try {
            if (null != user) {
                nodeList = this.userSerivce.getMenuTreeByUserId(user.getUserId());
                ownBtnList = this.userSerivce.getButtonByUserId(user.getUserId());
            }

            allBtnList = buttonService.getButtonList(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String menuJson = JsonUtil.getJsonString(nodeList, JsonUtil.OBJECT_TYPE_LIST);  // 菜单json

        mv.addObject("allBtnList", allBtnList);
        mv.addObject("ownBtnList", ownBtnList);
        mv.addObject("menuJson", menuJson);
        return mv;
    }

    /**
     * 获取按钮信息
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/getButton.do")
    private void getButton(HttpServletRequest request, HttpServletResponse response) {
        Button btn = new Button();
        String id = request.getParameter("id");

        try {
            if (StringUtil.isNotNullOrBlank(id)) {
                btn = this.buttonService.getButtonInfo(Integer.parseInt(id));
            }
        } catch (Exception e) {
            logger.error("获取按钮信息出错...", e);
        }

        JsonUtil.writeJsonToResponse(response, btn, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 编辑角按钮
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.POST, value = "/editButton.do")
    private void editButton(HttpServletRequest request, HttpServletResponse response) {
        Boolean success = false;
        Button btn = new Button();
        Result r = new Result();

        String id = request.getParameter("id");
        String btnTag = request.getParameter("btnTag");
        String btnTitle = request.getParameter("btnTitle");

        try {
            if (StringUtil.isNotNullOrBlank(id)) {
                btn.setId(Integer.parseInt(id));
                btn.setBtnTag(btnTag);
                btn.setBtnTitle(btnTitle);

                success = this.buttonService.updateButton(btn);
            }

            if (success) {
                r.setSuccess(true);
                r.setCode(Constant.EXECUTE_SUCCESS);
                r.setMessage("更新按钮信息成功!");
            } else {
                r.setSuccess(false);
                r.setCode(Constant.EXECUTE_FAILED);
                r.setMessage("更新按钮信息失败...");
            }
        } catch (Exception e) {
            logger.error("获取按钮信息出错...", e);
        }

        JsonUtil.writeJsonToResponse(response, r, JsonUtil.OBJECT_TYPE_BEAN);
    }

}
