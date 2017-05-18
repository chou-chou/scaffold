package com.hrp.controller.back;

import com.hrp.controller.common.BaseController;
import com.hrp.entity.business.TbProject;
import com.hrp.pojo.Result;
import com.hrp.service.ProjectService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RoleController
 *
 * @author KVLT
 * @date 2017-03-23.
 */
@Controller
@RequestMapping(value = "/b/project")
public class ProjectController extends BaseController {

    // TbProject模块基础路径
    private final static String BASE_PATH = "back/business/project/";  // --> WEB-INF/views/back/business/project/

    @Resource(name = "projectService")
    private ProjectService projectService;

    /**
     * 角色列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list")
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
            List<TbProject> proList = this.projectService.listAllProjectsByPId(pd);

            Map<String, String> QX = new HashMap<String, String>();
            QX.put("add", "1");
            QX.put("email", "1");
            QX.put("sms", "1");
            QX.put("del", "1");
            QX.put("cha", "1");
            QX.put("edit", "1");

            mv.addObject("proList", proList);
            mv.addObject("pd", pd);
            mv.addObject("page", page);
            mv.addObject("QX", QX);
            mv.setViewName(BASE_PATH + "project_list");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

    /**
     * 编辑页面
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editProject.do", method = RequestMethod.GET)
    public void editDictionary(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String roleId = request.getParameter("roleIds");//获取角色id
        String tag = request.getParameter("tag");

        PageData pd = this.getPageData();
        pd.put("roleId", roleId);
        TbProject project = null;
        try {
            project = this.projectService.getByProjectId(pd);//查询角色信息返回页面
            logger.info(project.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, project, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 新增/修改（For 新增/编辑）
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editProject.do", method = RequestMethod.POST)
    public void editDictionaryPost(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String tag = request.getParameter("tag");
        Result rc = new Result();

        PageData pd = this.getPageData();
        switch (tag) {
            case "ADD": // 新增
                try {
                    String proCode = StringUtil.isNotNullOrBlank(request.getParameter("proCode"))
                            ? request.getParameter("proCode") : "";
                    String proName = StringUtil.isNotNullOrBlank(request.getParameter("proName"))
                            ? request.getParameter("proName") : "";
                    String reportor = StringUtil.isNotNullOrBlank(request.getParameter("reportor"))
                            ? request.getParameter("reportor") : "";
                    String reportorTimeStr = StringUtil.isNotNullOrBlank(request.getParameter("reportorTime"))
                            ? request.getParameter("reportorTime") : "";
                    String proType = StringUtil.isNotNullOrBlank(request.getParameter("proType"))
                            ? String.valueOf(request.getParameter("proType")) : "";
                    String declareFundsStr = StringUtil.isNotNullOrBlank(request.getParameter("declareFunds"))
                            ? request.getParameter("declareFunds") : "";
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    Date reportorTime = sdf.parse(reportorTimeStr);
                    Double declareFunds = Double.valueOf(declareFundsStr);
                    TbProject project = new TbProject();
                    project.setProCode(proCode);
                    project.setProName(proName);
                    project.setReportor(reportor);
                    project.setReportTime(reportorTime);
                    project.setProType(proType);
                    project.setDeclareFunds(declareFunds);
                    Integer res = (Integer) this.projectService.add(project);  // mybatis insert后dictionary会获取实体数据，包括新增的id

                    if (res > 0) {
                        rc.setCode("0");
                        rc.setMessage("新增项目成功");
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
                    System.out.print(request.getParameter("id"));
                    TbProject project = new TbProject();
                    Boolean success = this.projectService.updateProject(pd);
                    if (success) {
                        rc.setCode("0");
                        rc.setMessage("更新项目成功");
                        rc.setSuccess(true);
                        rc.setData(request.getParameter("id"));

                    } else {
                        rc.setCode("1");
                        rc.setMessage("更新失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        JsonUtil.writeJsonToResponse(response, rc, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 删除
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/deleteProjects.do", method = RequestMethod.POST)
    public void removeDictionary(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String ids = request.getParameter("ids");
        String[] arr = ids.split(",");
        Result rc = new Result();
        int[] idsArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            idsArr[i] = Integer.parseInt(arr[i]);
        }
        try {
            Boolean success = this.projectService.deleteProjectById(idsArr);
            if (success) {
                rc.setCode("0");
                rc.setMessage("删除项目成功");

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
