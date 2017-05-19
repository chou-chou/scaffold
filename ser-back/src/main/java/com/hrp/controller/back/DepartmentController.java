package com.hrp.controller.back;

import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.Department;
import com.hrp.entity.system.TreeNode;
import com.hrp.pojo.Result;
import com.hrp.service.DepartmentService;
import com.hrp.utils.JsonUtil;
import com.hrp.utils.PageData;
import com.hrp.utils.lang.StringUtil;
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
 * Created by Resource on 2017/5/2.
 */
@Controller
@RequestMapping(value = "/b/department")
public class DepartmentController extends BaseController {

    // menu模块基础路径
    private final static String BASE_PATH = "back/system/department/";  // --> WEB-INF/views/system/dictionary/

    PageData pd = new PageData();

    @Resource(name = "departmentService")
    private DepartmentService departmentService;

    /**
     * 部门模块主页面
     */
    @RequestMapping(value = "/main.do", method = RequestMethod.GET)
    @MvcMapping(url = "/b/department/main.do", path = BASE_PATH + "deptList", type = MvcMapping.ViewType.JSP)
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = this.getModelAndView(BASE_PATH + "department_main");

        List<Department> deptList = new ArrayList();

        try {
            deptList = this.departmentService.listAllDepartment(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mv.addObject("deptList", deptList);
        return mv;
    }

    /**
     * 返回给前部门列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDepTree.do", method = RequestMethod.POST)
    public void getDepTree(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据

        String deptInfo = request.getParameter("deptInfo");

        PageData pd = new PageData();
        if (StringUtil.isNotBlank(deptInfo))     pd.put("deptInfo", deptInfo);

        List<TreeNode> deptList = new ArrayList();

        try {
            deptList = this.departmentService.selectDepartmentCascade(pd);
        } catch (Exception e) {
            logger.info("获取字典数据列表出错...");
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, deptList, JsonUtil.OBJECT_TYPE_LIST);  // 转换为标准json格式
    }

    /**
     * 编辑页面（For 新增/编辑）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editDepartment.do", method = RequestMethod.GET)
    public void editDictionary(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String deptId = request.getParameter("dicId");
        String tag = request.getParameter("tag");

        pd.put("deptId", deptId);
        Department dic = null;

        try {
            dic = this.departmentService.getByDeptId(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, dic, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 新增/修改（For 新增/编辑）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editDepartment.do", method = RequestMethod.POST)
    public void editDictionaryPost(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String tag = request.getParameter("tag");
        Result rc = new Result();

        pd = this.getPageData();

        switch (tag) {
            case "ADD": // 新增
                try {
                    String deptCode = StringUtil.isNotNullOrBlank(request.getParameter("deptCode"))
                            ? request.getParameter("deptCode") : "";
                    String deptName = StringUtil.isNotNullOrBlank(request.getParameter("deptName"))
                            ? request.getParameter("deptName") : "";
                    String supId = StringUtil.isNotNullOrBlank(request.getParameter("supId"))
                            ? request.getParameter("supId") : "";   // 父级Code
                    String described = StringUtil.isNotNullOrBlank(request.getParameter("described"))
                            ? request.getParameter("described") : "";
                    String address = StringUtil.isNotNullOrBlank(request.getParameter("address"))
                            ? String.valueOf(request.getParameter("address")) : "";
                    String remark = StringUtil.isNotNullOrBlank(request.getParameter("remark"))
                            ? request.getParameter("remark") : "";

                    Department department = new Department();
                    department.setDeptCode(deptCode);
                    department.setDeptName(deptName);
                    department.setSupId(supId);
                    department.setDescribed(described);
                    department.setAddress(address);
                    department.setDescribed(described);
                    department.setRemark(remark);

                    Integer res = (Integer) this.departmentService.saveDepartment(department);  // mybatis insert后dictionary会获取实体数据，包括新增的id
                    Department supDep = this.departmentService.getBySupDep(supId);  // 有可能是根级root -> -1

                    department.setSupDepartment(supDep);  // 父级bean
                    if (res > 0) {
                        rc.setCode("0");
                        rc.setMessage("新增部门成功");
                        rc.setData(department); // 返回部门数据（包括父级）
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
                    Boolean success = this.departmentService.updateDepartment(pd);
                    if (success) {
                        rc.setCode("0");
                        rc.setMessage("更新成功");
                        rc.setSuccess(true);
                        rc.setData(request.getParameter("deptId"));
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
    @RequestMapping(value = "/removeDepartment.do", method = RequestMethod.POST)
    public void removeDictionary(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String deptId = request.getParameter("deptId");

        Result rc = new Result();
        pd = this.getPageData();
        pd.put("ids", new String[]{deptId});
        Department dept=new Department();

        try {
            Boolean success = this.departmentService.deleteByIds(pd);
            if (success) {
                rc.setCode("0");
                rc.setMessage("删除部门成功");
                rc.setData(deptId);
            } else {
                rc.setCode("1");
                rc.setMessage("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, rc, JsonUtil.OBJECT_TYPE_BEAN);
    }

}
