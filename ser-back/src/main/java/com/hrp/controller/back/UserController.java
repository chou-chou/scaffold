package com.hrp.controller.back;

import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.Menu;
import com.hrp.service.UserService;
import com.hrp.utils.AppUtil;
import com.hrp.utils.Jurisdiction;
import com.hrp.utils.PageData;
import com.hrp.utils.lang.StringUtil;
import com.hrp.utils.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController
 * 用户模块
 * @author KVLT
 * @date 2017-03-23.
 */
@Controller
@RequestMapping(value = "/b/user")
public class UserController extends BaseController {

    // 用户模块基础路径
    private final static String BASE_PATH = "back/system/user/";  // --> WEB-INF/views/system/user/

    @Resource(name = "userService")
    private UserService userService;

    /**
     * 角色列表
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    private ModelAndView list() {
        ModelAndView mv = this.getModelAndView();
        Page page = this.getPage();
        PageData pd = this.getPageData();

        try {
            String userInfo = pd.getString("userInfo");
            if (StringUtil.isNotNullOrBlank(userInfo)) {
                pd.put("userInfo", userInfo.trim());
            }
            page.setPd(pd);
            List<PageData> userList = this.userService.listPdPageUser(page);

            Map<String, String> QX = new HashMap<String, String>();
            QX.put("add", "1");
            QX.put("email", "1");
            QX.put("sms", "1");
            QX.put("del", "1");
            QX.put("cha", "1");
            QX.put("edit", "1");

            mv.addObject("userList", userList);
            mv.addObject("pd", pd);
            mv.addObject("page", page);
            mv.addObject("QX", QX);
            mv.setViewName(BASE_PATH + "user_list");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

    /**
     * 请求新增菜单页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd() throws Exception {
        ModelAndView mv = this.getModelAndView();

        return mv;
    }

    /**
     * 保存菜单信息
     * @param menu
     * @return
     */
    @RequestMapping(value="/add")
    public ModelAndView add(Menu menu)throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"保存菜单");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        mv.setViewName("redirect:/menu.do?MSG=change&MENU_ID="+menu.getSupId()); //保存成功跳转到列表页面
        return mv;
    }

    /**
     * 删除菜单
     * @param MENU_ID
     */
    @RequestMapping(value="/delete")
    @ResponseBody
    public Object delete(@RequestParam String MENU_ID)throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"删除菜单");
        Map<String,String> map = new HashMap<String,String>();
        String errInfo = "";

        map.put("result", errInfo);
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 请求编辑菜单页面
     * @param
     * @return
     */
    @RequestMapping(value="/toEdit")
    public ModelAndView toEdit(String id)throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();

        return mv;
    }

    /**
     * 保存编辑
     * @param
     * @return
     */
    @RequestMapping(value="/edit")
    public ModelAndView edit(Menu menu)throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"修改菜单");
        ModelAndView mv = this.getModelAndView();

        mv.setViewName("redirect:/menu.do?MSG=change&MENU_ID="+menu.getSupId()); //保存成功跳转到列表页面
        return mv;
    }

    /**
     * 请求编辑菜单图标页面
     * @param
     * @return
     */
    @RequestMapping(value="/toEditicon")
    public ModelAndView toEditicon(String MENU_ID)throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        try{
            pd = this.getPageData();
            pd.put("MENU_ID",MENU_ID);
            mv.addObject("pd", pd);
            mv.setViewName(BASE_PATH + "user_icon");
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 保存菜单图标
     * @param
     * @return
     */
    @RequestMapping(value="/editicon")
    public ModelAndView editicon()throws Exception{
        logBefore(logger, Jurisdiction.getUsername()+"修改菜单图标");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();

        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 显示菜单列表ztree(菜单管理)
     * @param model
     * @return
     */
    @RequestMapping(value="/listAllMenu")
    public ModelAndView listAllMenu(Model model, String MENU_ID)throws Exception{
        ModelAndView mv = this.getModelAndView();

        return mv;
    }

}
