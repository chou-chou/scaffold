package com.hrp.controller.back;

import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.Menu;
import com.hrp.entity.system.Role;
import com.hrp.entity.system.User;
import com.hrp.entity.system.UserRoleLink;
import com.hrp.pojo.Result;
import com.hrp.service.RoleService;
import com.hrp.service.UserService;
import com.hrp.utils.*;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
    @Resource(name = "roleService")
    private RoleService roleService;

    /**
     * 角色列表
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    @MvcMapping(tag = "userlist", path = BASE_PATH + "user_list", type = MvcMapping.ViewType.JSP)
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
            List<Role> roleList = this.roleService.listAllRolesByPId(pd);

            Map<String, String> QX = new HashMap<String, String>();
            QX.put("add", "1");
            QX.put("email", "1");
            QX.put("sms", "1");
            QX.put("del", "1");
            QX.put("cha", "1");
            QX.put("edit", "1");

            mv.addObject("userList", userList);
            mv.addObject("roleList", roleList);
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

    /**
     * 编辑页面
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editUser.do", method = RequestMethod.GET)
    public void editUser(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String userId = request.getParameter("userId");//获取用户id
        String tag = request.getParameter("tag");
        PageData pd = this.getPageData();
        pd.put("userId", userId);
        User user = null;

        try {
            user = this.userService.getByUserId(pd);//查询用户信息返回页面
            logger.info(user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, user, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 编辑页面
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkoutAccount.do", method = RequestMethod.GET)
    public void checkoutAccount(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String account = request.getParameter("account");//获取用户登录名
        String userId = request.getParameter("userId");
        Boolean rc = false;
        PageData pd = new PageData();
        pd.put("userId",userId);
        User accountUser = null;
        User idUser = null;
        try {
            if(null !=userId && !"".equals(userId)){
                idUser = this.userService.getByUserId(pd);
                String dbAcction = idUser.getAccount();
                if (dbAcction.equals(account)){
                    rc = true;
                }else{
                    accountUser = this.userService.getUserByLoginName(account);//查询用户信息返回页面
                    if(null == accountUser ||  "".equals(accountUser)){
                        rc = true;
                    }
                }
            }else{
                accountUser = this.userService.getUserByLoginName(account);//查询用户信息返回页面
                if(null == accountUser ||  "".equals(accountUser)){
                    rc = true;
                }
            }


            response.getWriter().print(rc ? "true" : "false");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //JsonUtil.writeJsonToResponse(response, rc, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 删除
     * @param request
     * @param response
     */
    @RequestMapping(value = "/deleteUsers.do", method = RequestMethod.POST)
    public void removeUser(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String userIds = request.getParameter("userIds");
        String[] userIdsArr= userIds.split(",");
        Result rc = new Result();
        try {
            Boolean success = this.userService.deleteAllU(userIdsArr);
            if (success) {
                rc.setCode("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, rc, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 新增/修改（For 新增/编辑）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editUser.do", method = RequestMethod.POST)
    public void editUserPost(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String tag = request.getParameter("tag");
        Result rc = new Result();
        PageData pd = this.getPageData();

        switch (tag) {
            case "ADD": // 新增
                try {
                    String userName = StringUtil.isNotNullOrBlank(request.getParameter("userName"))
                            ? request.getParameter("userName") : "";
                    String account = StringUtil.isNotNullOrBlank(request.getParameter("account"))
                            ? request.getParameter("account") : "";
                    String isEnabled = StringUtil.isNotNullOrBlank(request.getParameter("isEnabled"))
                            ? request.getParameter("isEnabled") : "";
                    String telephone = StringUtil.isNotNullOrBlank(request.getParameter("telephone"))
                            ? request.getParameter("telephone") : "0";
                    String email = StringUtil.isNotNullOrBlank(request.getParameter("email"))
                            ? String.valueOf(request.getParameter("email")) : "";
                    String remark = StringUtil.isNotNullOrBlank(request.getParameter("remark"))
                            ? request.getParameter("remark") : "";
                    boolean  enabled=false;
                    if("1".equals(isEnabled)){
                      enabled = true;
                    }
                    String password = "888888";//初始密码
                    String passwordMD5 = EncryptUtil.md5Password(userName, password);//加密的密码
                    User user = new User();
                    user.setUserName(userName);
                    user.setAccount(account);
                    user.setPassword(passwordMD5);
                    user.setEnabled(enabled);
                    user.setEmail(email);
                    user.setRemark(remark);
                    user.setTelephone(telephone);
                    Integer res = (Integer) this.userService.addUser(user);  // 新增用户插入数据库

                    if (res > 0) {
                        rc.setCode("0");
                        rc.setMessage("新增用户成功");
                        rc.setData(tag);
                        rc.setSuccess(true);
                    } else {
                        rc.setCode("1");
                        rc.setMessage("新增失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "EDIT": // 编辑
                try {
                    System.out.print(request.getParameter("userId"));
                    Boolean  success=this.userService.updateUser(pd);
                    if (success) {
                        rc.setCode("0");
                        rc.setMessage("更新成功");
                        rc.setSuccess(true);
                        rc.setData(request.getParameter("userId"));

                    } else {
                        rc.setCode("1");
                        rc.setMessage("更新失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default: break;
        }

        JsonUtil.writeJsonToResponse(response, rc, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 设置角色
     * @param request
     * @param response
     */
    @RequestMapping(value = "/setRole.do", method = RequestMethod.GET)
    public void setRole(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String userId = request.getParameter("userId");//获取用户id
        String tag = request.getParameter("tag");

        PageData pd = this.getPageData();
        pd.put("userId", userId);
        List<UserRoleLink> userRoleLinkList = null;
        Result rc = new Result();
        String roleIds="";
        UserRoleLink userRoleLink=new UserRoleLink();
        try {
            userRoleLinkList = this.userService.findRoleIdByUserId(pd);//查询用户角色id信息返回页面
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, userRoleLinkList, JsonUtil.OBJECT_TYPE_LIST);
    }

    @RequestMapping(value = "saveRole.do" ,method =RequestMethod.POST )
    public  void  saveRole(HttpServletRequest request, HttpServletResponse response){
        Result rc = new Result();
        String userId = request.getParameter("userId");//获取用户id
        String roleIds=request.getParameter("roleIds");//获取设置的角色id
        String[] roleidArr=roleIds.split(",");
        List<UserRoleLink> userRoleLinkList=new ArrayList<UserRoleLink>();
        //List a=new com.sun.tools.javac.util.List();
        UserRoleLink userRoleLink=null;
        boolean success=true;
        try {
            if(null==userId || "".equals(userId)){
                success=false;
            }else{
                userService.deleteUserRole(userId);
                if(roleidArr.length>0){
                    for(int i=0;i<roleidArr.length;i++){
                        userRoleLink=new UserRoleLink();
                        userRoleLink.setUserId(userId);
                        userRoleLink.setRoleId(Integer.valueOf(roleidArr[i]));
                        userRoleLink.setEnabled(true);
                        userRoleLinkList.add(userRoleLink);//整理用户插入的角色信息
                    }
                    userService.insertUserRole(userRoleLinkList);//批量插入角色信息
                }
            }

            if (success) {
                rc.setCode("0");
                rc.setMessage("设置角色成功成功");
                rc.setSuccess(true);
            }else{
                rc.setCode("1");
                rc.setMessage("设置角色失败");
                rc.setSuccess(false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, rc, JsonUtil.OBJECT_TYPE_BEAN);
    }

}
