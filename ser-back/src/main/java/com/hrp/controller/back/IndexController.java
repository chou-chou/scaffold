package com.hrp.controller.back;

import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.Menu;
import com.hrp.entity.system.User;
import com.hrp.pojo.ResultCode;
import com.hrp.service.MenuService;
import com.hrp.service.UserService;
import com.hrp.utils.Constant;
import com.hrp.utils.EncryptUtil;
import com.hrp.utils.SystemValidate;
import com.hrp.utils.lang.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * IndexController
 *
 * @author KVLT
 * @date 2017-03-23.
 */
@Controller
@RequestMapping("/b/")
public class IndexController extends BaseController {

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "menuService")
    private MenuService menuService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String _root(){
        return Constant.INDEX_PAGE;
    }

    @RequestMapping(value = "index.do")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView(Constant.INDEX_PAGE);

        /**
         * 获取菜单树
         */
        return mv;
    }

    @RequestMapping("refreshDB.do")
    @ResponseBody
    public Map<String, Object> refreshDB() {
        resultMap.put("status", 200);
        return resultMap;
    }

    //踢出用户
    @RequestMapping(value="kickouting")
    @ResponseBody
    public String kickouting() {
        return "kickout";
    }

    //被踢出后跳转的页面
    @RequestMapping(value="kickout")
    public String kickout() {
        return Constant.KICKOUT;
    }

    /**
     * 获取当前用户的菜单列表
     * @return
     */
    @RequestMapping(value = "anon/fetchMenus.do", method = RequestMethod.GET)
    //@ResponseBody  // 处理AJAX请求，返回响应的内容，而不是 View Name
    public @ResponseBody List<Menu> fetchMenus() {
        List<Menu> menuList = new ArrayList<Menu>();
        String json = "[{"
                + "F_ModuleId:1, F_ParentId:0, F_EnCode:SysManage, F_FullName:系统管理, F_Icon:"
                + "Fa fa-desktop, F_UrlAddress:null, F_Target:expand, F_IsMenu:0, F_AllowExpand:1, F_IsPublic:"
                + "0, F_AllowEdit:null, F_AllowDelete:null, F_SortCode:1, F_DeleteMark:0, F_EnabledMark:"
                + "1, F_Description:null, F_CreateDate:null, F_CreateUserId:null, F_CreateUserName:"
                + "null, F_ModifyDate:2015-11-17 11:22:46, F_ModifyUserId:System, F_ModifyUserName:超级管理员"
                + " },{"
                + "F_ModuleId:8, F_ParentId:2, F_EnCode:OrganizeManage, F_FullName:机构管理, F_Icon:"
                + "Fa fa-sitemap, F_UrlAddress:/BaseManage/Organize/Index, F_Target:iframe, F_IsMenu:"
                + "1, F_AllowExpand:1, F_IsPublic:0, F_AllowEdit:null, F_AllowDelete:null, F_SortCode:"
                + "1, F_DeleteMark:0, F_EnabledMark:1, F_Description:null, F_CreateDate:null, F_CreateUserId:"
                + "null, F_CreateUserName:null, F_ModifyDate:2016-04-29 11:55:28, F_ModifyUserId:"
                + "System, F_ModifyUserName:超级管理员}]";
        try {
            menuList = menuService.listAllMenu();

            // TODO 转换为json
        } catch (Exception e) {

        }

        return menuList;
    }

    @RequestMapping(value = "register.do", method = RequestMethod.GET)
    public String register() {
        return Constant.BACK_REGISTER_PAGE;
    }

    @RequestMapping(value = "login.do", method = RequestMethod.GET)
    public String login_GET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject subject = SecurityUtils.getSubject();

        // 判断会话，有则安全退出
        if (subject.getSession() != null) {
            subject.logout();
        }

        boolean flag = SystemValidate.validate(); // 验证系统是否有效
        if (subject.isAuthenticated() || subject.isRemembered()) {
            if (flag)
                return Constant.INDEX_PAGE;
            else
                return Constant.AUTHORITY_PAGE;
        } else {
            return Constant.BACK_LOGIN_PAGE;
        }
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    private String doLogout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            try {
                subject.logout();
            } catch (Exception e) {

            }
        }

        return Constant.BACK_LOGIN_PAGE;  // 建议在调用subject.logout后立即向终端重定向一个新的视图或页面。这样即能保证与安全相关的Cookie都能像预期的一样被删除
    }

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    private ModelAndView loginPost(HttpServletRequest request) {
        ModelAndView mv = getModelAndView();

        //已经登录过，直接进入主页
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated() && subject.isRemembered()) {
            boolean isAuthorized = Boolean.valueOf(subject.getSession().getAttribute("isAuthorized").toString());
            if (isAuthorized) {
                mv.setViewName(Constant.BACK_MAIN_PAGE);
                return mv;
            }
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");  // 记住我

        logger.info(username + " " + password + " " + rememberMe);

        //默认首页，第一次进来
        if (StringUtil.isEmpty(username)) {
            mv.setViewName(Constant.LOCK_PAGE);
            return mv;
        }

        //密码加密+加盐
        //password = EncryptUtil.getPassword(password, username);

        logger.info("加密的密码： " + password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        if (StringUtil.isNotNullOrBlank(rememberMe) && Constant.S_REMEMBER_ME.equalsIgnoreCase(rememberMe)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        subject = SecurityUtils.getSubject();
        String msg;
        try {
            subject.login(token);  // 去往ShiroRealm里面doGetAuthenticationInfo方法进行验证

            logger.info("认证结果： " + subject.isAuthenticated());
            //通过认证
            if (subject.isAuthenticated()) {
                Set<String> roles = userService.findAllRoleNamesByUsername(username);
                if (Constant.S_ADMIN.equalsIgnoreCase(username) || !roles.isEmpty()) {
                    subject.getSession().setAttribute("isAuthorized", true);

                    mv.setViewName(Constant.BACK_MAIN_PAGE);
                    return mv;
                } else {//没有授权
                    msg = "您没有得到相应的授权！";
                    mv.addObject("message", new ResultCode("1", msg));
                    subject.getSession().setAttribute("isAuthorized", false);
                    logger.error(msg);

                    mv.setViewName(Constant.BACK_LOGIN_PAGE);
                    return mv;
                }
            } else {
                mv.setViewName(Constant.BACK_LOGIN_PAGE);
                return mv;
            }
            //0 未授权 1 账号问题 2 密码错误  3 账号密码错误
        } catch (UnknownAccountException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            mv.addObject("message", new ResultCode("1", msg));
            logger.error(msg);
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect";
            mv.addObject("message", new ResultCode("2", msg));
            logger.error(msg);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多, 账户锁定10分钟";
            mv.addObject("message", new ResultCode("3", msg));
            logger.error(msg);
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            mv.addObject("message", new ResultCode("1", msg));
            logger.error(msg);
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            mv.addObject("message", new ResultCode("1", msg));
            logger.error(msg);
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            mv.addObject("message", new ResultCode("1", msg));
            logger.error(msg);
        } catch (ConcurrentAccessException e) {
            msg = "不允许多处登录，只能登录一次";
            mv.addObject("message", new ResultCode("1", msg));
            logger.error(msg);
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            mv.addObject("message", new ResultCode("1", msg));
            logger.error(msg);
        }

        // 验证是否登陆成功
        if (subject.isAuthenticated()) {
            logger.info("用户[" + username + "]登录认证通过！");
        } else {
            token.clear();
        }

        String exceptionClassName = (String) request.getAttribute(Constant.SHIRO_LOGIN_FAILURE);
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            mv.addObject("message", new ResultCode("1", msg));
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect";
            mv.addObject("message", new ResultCode("2", msg));
        } else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            mv.addObject("message", new ResultCode("1", msg));
        }

        mv.setViewName(Constant.BACK_LOGIN_PAGE);
        return mv;
    }

    /**
     * 后端用户注册
     * @param request
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    public String register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 加密用户输入的密码，得到密码的盐
        String encodedPwd = EncryptUtil.md5Password(username, password);

        return Constant.BACK_MAIN_PAGE;
    }

    //----------------oauth 认证------------------
    /*@RequestMapping(value = "/oauth/{type}/callback", method = RequestMethod.GET)
    public String callback(@RequestParam(value = "code", required = true) String code, @PathVariable(value = "type") String type,
                           HttpServletRequest request, Model model) {
        model.addAttribute("oAuthServices", oAuthServices.getAllOAuthServices());
        try {
            CustomOAuthService oAuthService = oAuthServices.getOAuthService(type);
            Token accessToken = oAuthService.getAccessToken(null, new Verifier(code));
            //第三方授权返回的用户信息
            OAuthUser oAuthInfo = oAuthService.getOAuthUser(accessToken);
            //查询本地数据库中是否通过该方式登陆过
            OAuthUser oAuthUser = oAuthUserService.findByOAuthTypeAndOAuthId(oAuthInfo.getoAuthType(), oAuthInfo.getoAuthId());
            //未建立关联，转入用户注册界面
            if (oAuthUser == null) {
                model.addAttribute("oAuthInfo", oAuthInfo);
                return BACK_REGISTER_PAGE;
            }

            //如果已经过关联，直接登录
            User user = userService.get(User.class, oAuthUser.getUserId());
            return loginByAuth(user);
        }catch (Exception e){
            String msg = "连接"+type+"服务器异常. 错误信息为："+e.getMessage();
            model.addAttribute("message", new ResultCode("1", msg));
            LOGGER.error(msg);
            return BACK_LOGIN_PAGE;
        }

    }

    @RequestMapping(value = "/oauth/register", method = RequestMethod.POST)
    public String register_oauth(User user, @RequestParam(value = "oAuthType", required = false, defaultValue = "") String oAuthType,
                                 @RequestParam(value = "oAuthId", required = true, defaultValue = "") String oAuthId,
                                 HttpServletRequest request,Model model) {
        model.addAttribute("oAuthServices", oAuthServices.getAllOAuthServices());
        OAuthUser oAuthInfo = new OAuthUser();
        oAuthInfo.setoAuthId(oAuthId);
        oAuthInfo.setoAuthType(oAuthType);
        //保存用户
        user.setPassword(EncryptUtil.getPassword(user.getPassword(),user.getLoginName()));
        String userId=userService.save(user).toString();
        //建立第三方账号关联
        OAuthUser oAuthUser=oAuthUserService.findByOAuthTypeAndOAuthId(oAuthType,oAuthId);
        if(oAuthUser==null&&!oAuthType.equals("-1")){
            oAuthInfo.setUserId(userId);
            oAuthUserService.save(oAuthInfo);
        }
        //关联成功后登陆
        return loginByAuth(user);
    }

    public String loginByAuth(User user){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), user.getPassword());
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        //通过认证
        if (subject.isAuthenticated()) {
            return BACK_MAIN_PAGE;
        } else {
            return BACK_LOGIN_PAGE;
        }
    }*/

    /**
     * 校验当前登录名/邮箱的唯一性
     * @param loginName 登录名
     * @param userId 用户ID（用户已经存在，改回原来的名字还是唯一的）
     * @return
     */
    @RequestMapping(value = "/oauth/checkUnique", method = RequestMethod.POST)
    @ResponseBody
    public Map checkExist(String loginName, String userId) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        User user = null;

        try {
            user = userService.getUserByLoginName(loginName);
        } catch (Exception e) {

        }

        //用户不存在，校验有效
        if (user == null) {
            map.put("valid", true);
        } else {
            if(!StringUtil.isEmpty(userId )&& userId.equals(user.getUsername())){
                map.put("valid",true);
            }else {
                map.put("valid", false);
            }
        }
        return map;
    }

    private boolean isRelogin(User user) {
        Subject us = SecurityUtils.getSubject();
        if (us.isAuthenticated()) {
            return true;  // 参数未改变，无需重新登录，默认为已经登录
        }
        return false;  // 需要重新登录
    }

}
