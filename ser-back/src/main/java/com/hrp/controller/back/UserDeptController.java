package com.hrp.controller.back;

import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.TreeNode;
import com.hrp.entity.system.UserDept;
import com.hrp.pojo.Result;
import com.hrp.service.DepartmentService;
import com.hrp.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * UserController
 * 用户模块
 *
 * @author KVLT
 * @date 2017-03-23.
 */
@Controller
@RequestMapping(value = "/b/userDept")
public class UserDeptController extends BaseController {

    // 用户模块基础路径
    private final static String BASE_PATH = "back/system/userDept/";  // --> WEB-INF/views/system/user/

    @Resource(name = "userService")
    private UserService userService;
    @Resource
    private DepartmentService departmentService;

    /**
     * 用户列表
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    @MvcMapping(tag = "userDept:list", path = BASE_PATH + "userDept_list", type = MvcMapping.ViewType.JSP)
    private ModelAndView userList() {
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
            mv.addObject("userList", userList);
            mv.addObject("pd", pd);
            mv.addObject("page", page);
            mv.setViewName(BASE_PATH + "userDept_list");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 返回给前端部门树信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDepTree.do", method = RequestMethod.GET)
    public void getDepTree(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据

        String deptInfo = request.getParameter("deptInfo");

        PageData pd = new PageData();
        if (StringUtil.isNotBlank(deptInfo)) pd.put("deptInfo", deptInfo);

        List<TreeNode> deptList = new ArrayList();

        try {
            deptList = this.departmentService.selectDepartmentCascade(pd);
        } catch (Exception e) {
            logger.info("获取部门数据列表出错..");
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, deptList, JsonUtil.OBJECT_TYPE_LIST);  // 转换为标准json格式
    }

    @RequestMapping(value = "/getUserDept.do", method = RequestMethod.POST)
    public void getUserDept(HttpServletRequest request, HttpServletResponse response) {
        PageData pd = this.getPageData();
        String userId = StringUtil.isNotNullOrBlank(request.getParameter("userId"))
                ? request.getParameter("userId") : "";
        UserDept userDept = null;
        pd.put("userId", userId);
        String[] dept;
        try {
            userDept = this.departmentService.getUserDept(pd);  // 根据用户编号查询
            if (null != userDept) {
                String deptId = userDept.getDeptId();  //获取部门信息
                if (null != deptId) {
                    dept = deptId.split("/");  // 分割成数组
                    userDept.setDeptArr(dept);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonUtil.writeJsonToResponse(response, userDept, JsonUtil.OBJECT_TYPE_BEAN);


    }

    @RequestMapping(value = "/saveUserDept.do", method = RequestMethod.POST)
    public void saveUserDept(HttpServletRequest request, HttpServletResponse response) {
        Result rc=new Result();
        PageData pd = this.getPageData();
        String userId = StringUtil.isNotNullOrBlank(request.getParameter("userId"))
                ? request.getParameter("userId") : "";
        String deptId = StringUtil.isNotNullOrBlank(request.getParameter("deptId"))
                ? request.getParameter("deptId") : "";
        UserDept userDept = null;
        Integer res = 0;
        Integer resu = 0;
        try {
            userDept = this.departmentService.getUserDept(pd);  // 根据用户编号查询

            if (null != userDept) {
                userDept.setUserId(userId);
                userDept.setDeptId(deptId);
                res = (Integer) this.departmentService.upadteUserDept(userDept);  // 更新数据
            } else {
                UserDept insUserDept = new UserDept();
                insUserDept.setUserId(userId);
                insUserDept.setDeptId(deptId);
                resu = (Integer) this.departmentService.insUserDept(insUserDept);  // 插入
            }
            if(res>0 || resu>0){
                rc.setCode("0");
                rc.setMessage("更新成功");
            }else{
                rc.setCode("0");
                rc.setMessage("数据未改变");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonUtil.writeJsonToResponse(response, rc, JsonUtil.OBJECT_TYPE_BEAN);


    }


}
