package com.hrp.controller.back;

import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.Button;
import com.hrp.entity.system.Role;
import com.hrp.entity.system.TreeNode;
import com.hrp.entity.system.User;
import com.hrp.pojo.Result;
import com.hrp.service.ButtonService;
import com.hrp.service.MenuService;
import com.hrp.service.RoleService;
import com.hrp.service.UserService;
import com.hrp.utils.Constant;
import com.hrp.utils.JsonUtil;
import com.hrp.utils.PageData;
import com.hrp.utils.lang.StringUtil;
import com.hrp.utils.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * RoleController
 *
 * @author KVLT
 * @date 2017-03-23.
 */
@Controller
@RequestMapping(value = "/b/role")
public class RoleController extends BaseController {

    // 角色模块基础路径
    private final static String BASE_PATH = "back/system/role/";  // --> WEB-INF/views/system/button/

    @Resource(name = "roleService")
    private RoleService roleService;

    @Resource(name = "menuService")
    private MenuService menuService;

    @Resource
    private ButtonService buttonService;

    @Resource
    private UserService userSerivce;

    /**
     * 角色列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @MvcMapping(tag = "role:list", path = BASE_PATH + "role_list", type = MvcMapping.ViewType.JSP)
    private ModelAndView list(HttpSession session){
        ModelAndView mv = this.getModelAndView();
        Page page = this.getPage();
        PageData pd = this.getPageData();
        List<TreeNode> nodeList = new ArrayList();
        List<Button> allBtnList = new ArrayList();

        User user = (User)session.getAttribute(Constant.CURRENT_USER);  // 当前用户

        try {
            if (null != user) {
                nodeList = this.userSerivce.getMenuTreeByUserId(user.getUserId());
            }

            allBtnList = buttonService.getButtonList(pd);

            String roleInfo = pd.getString("roleInfo");
            if (StringUtil.isNotNullOrBlank(roleInfo)) {
                pd.put("userInfo", roleInfo.trim());
            }
            page.setPd(pd);
            List<Role> roleList = this.roleService.listAllRolesByPId(pd);
            Map<String, String> QX = new HashMap<String, String>();
            QX.put("add", "1");
            QX.put("email", "1");
            QX.put("sms", "1");
            QX.put("del", "1");
            QX.put("cha", "1");
            QX.put("edit", "1");

            String menuJson = JsonUtil.getJsonString(nodeList, JsonUtil.OBJECT_TYPE_LIST);  // 菜单json
            mv.addObject("menuJson", menuJson);
            mv.addObject("allBtnList", allBtnList);

            mv.addObject("roleList", roleList);
            mv.addObject("pd", pd);
            mv.addObject("page", page);
            mv.addObject("QX", QX);
            mv.setViewName(BASE_PATH + "role_list");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

    /**
     * 编辑页面
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editRole.do", method = RequestMethod.GET)
    public void editRole(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String roleId = request.getParameter("roleIds");//获取角色id
        String tag = request.getParameter("tag");

        PageData pd = this.getPageData();
        pd.put("roleId", roleId);
        Role role = null;
        try {
            role = this.roleService.getByRoleId(pd);//查询角色信息返回页面
            logger.info(role.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, role, JsonUtil.OBJECT_TYPE_BEAN);
    }



    /**
     * 新增/修改（For 新增/编辑）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editRole.do", method = RequestMethod.POST)
    public void editRolePost(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String tag = request.getParameter("tag");
        Result rc = new Result();
        PageData pd = this.getPageData();

        switch (tag) {
            case "ADD": // 新增
                try {
                    String code = StringUtil.isNotNullOrBlank(request.getParameter("code"))
                            ? request.getParameter("code") : "";
                    String roleName = StringUtil.isNotNullOrBlank(request.getParameter("roleName"))
                            ? request.getParameter("roleName") : "";
                    String isSys = StringUtil.isNotNullOrBlank(request.getParameter("isSys"))
                            ? request.getParameter("isSys") : "";
                    String supId = StringUtil.isNotNullOrBlank(request.getParameter("supId"))
                            ? request.getParameter("supId") : "";
                    String orders = StringUtil.isNotNullOrBlank(request.getParameter("orders"))
                            ? String.valueOf(request.getParameter("orders")) : "";
                    String remark = StringUtil.isNotNullOrBlank(request.getParameter("remark"))
                            ? request.getParameter("remark") : "";

                    Role role = new Role();
                    role.setCode(code);
                    role.setRoleName(roleName);
                    role.setIsSys(isSys);
                    role.setOrders(orders);
                    role.setRemark(remark);
                    role.setSupId(supId);
                    Integer res = (Integer) this.roleService.add(role);  // mybatis insert后dictionary会获取实体数据，包括新增的id

                    if (res > 0) {
                        rc.setCode("0");
                        rc.setMessage("新增角色成功");
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
                    System.out.print(request.getParameter("roleId"));
                    Role role=new Role();
                  Boolean  success=this.roleService.updateRole(pd);
                    if (success) {
                        rc.setCode("0");
                        rc.setMessage("更新成功");
                        rc.setSuccess(true);
                        rc.setData(request.getParameter("roleId"));

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
     * 删除
     * @param request
     * @param response
     */
    @RequestMapping(value = "/deleteRoles.do", method = RequestMethod.POST)
    public void removeRole(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String roleIds = request.getParameter("roleIds");
        String[] arr= roleIds.split(",");
        Result rc = new Result();
        int[] roleIdsArr=new int[arr.length];
        for(int i=0;i<arr.length;i++){
            roleIdsArr[i] = Integer.parseInt(arr[i]);
        }
        try {
            Boolean success = this.roleService.deleteRoleById(roleIdsArr);
            if (success) {
                rc.setCode("0");
                rc.setMessage("删除成功");

            } else {
                rc.setCode("1");
                rc.setMessage("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, rc, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 跳转到角色按钮编辑页面
     * @param request
     */
    @RequestMapping(value = "/editRoleButton.do", method = RequestMethod.GET)
    public void toEditRoleButton(HttpServletRequest request, HttpServletResponse response) {
        String roleId = request.getParameter("roleId");
        List<Button> ownBtnList = new ArrayList<>();

        try {
            ownBtnList = this.roleService.getButtonByRoleId(Integer.parseInt(roleId));
        } catch (Exception e) {

        }

        JsonUtil.writeJsonToResponse(response, ownBtnList, JsonUtil.OBJECT_TYPE_LIST);

    }

    /**
     * 编辑角色按钮
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editRoleButton.do", method = RequestMethod.POST)
    public void editRoleButton(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String roleId = request.getParameter("roleId");
        String[] btnIds = request.getParameterValues("btnId");

        List<Button> ownBtnList;  // 该角色已有button权限

        Set<Integer> ownBtnIdSet = new HashSet();
        Set<Integer> fromPageBtnIdSet = new HashSet();

        Set<Integer> temp = new HashSet<>(); // 临时存放

        for (String btnId : btnIds) {
            fromPageBtnIdSet.add(Integer.parseInt(btnId));
        }

        Result rc = new Result();

        try {
            ownBtnList = this.roleService.getButtonByRoleId(Integer.parseInt(roleId));
            for (Button button : ownBtnList) {
                ownBtnIdSet.add(button.getId());
            }

            /**
             * 1、新增
             */
            temp.clear();
            temp.addAll(fromPageBtnIdSet);
            temp.removeAll(ownBtnIdSet);

            Integer[] toSave = temp.toArray(new Integer[0]);
            boolean success = roleService.batchSaveRoleBtn(Integer.parseInt(roleId), toSave);

            /**
             * 2、删除
             */
            temp.clear();
            temp.addAll(ownBtnIdSet);
            temp.removeAll(fromPageBtnIdSet);
            Integer[] toDelete = temp.toArray(new Integer[0]);
            success &= roleService.batchDeleteRoleBtn(Integer.parseInt(roleId), toDelete);

            if (success) {
                rc.setCode(Constant.EXECUTE_SUCCESS);
                rc.setSuccess(true);
                rc.setMessage("更新成功!");
            } else {
                rc.setCode(Constant.EXECUTE_FAILED);
                rc.setSuccess(false);
                rc.setMessage("更新失败...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, rc, JsonUtil.OBJECT_TYPE_BEAN);
    }
}
