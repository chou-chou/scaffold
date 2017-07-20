package com.hrp.controller.back;

import com.alibaba.fastjson.JSONArray;
import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.business.TbIndexTable;
import com.hrp.entity.business.TbProject;
import com.hrp.entity.business.TbProjectOpinion;
import com.hrp.entity.system.User;
import com.hrp.pojo.Result;
import com.hrp.service.IndexTableService;
import com.hrp.service.ProjectOpinionService;
import com.hrp.service.ProjectService;
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
@RequestMapping(value = "/b/project")
public class ProjectController extends BaseController {

    // TbProject模块基础路径
    private final static String BASE_PATH = "back/business/project/";  // --> WEB-INF/views/back/business/project/

    @Resource(name = "projectService")
    private ProjectService projectService;
    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "projectOpinionService")
    private ProjectOpinionService projectOpinionService;

    @Resource(name = "indexTableService")
    private IndexTableService indexTableService;


    /**
     * 查询所有记录
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list.do")
    @MvcMapping(tag = "project:list", path = BASE_PATH + "project_list", type = MvcMapping.ViewType.JSP)
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
            pd.put("CODE", Constant.EXPER);
            List<TbProject> proList = this.projectService.listAllProjectsByPId(pd);

            List<PageData> userList = userService.listAllUserByRoldCode(pd);
            List<TbIndexTable> indexTableList = indexTableService.listAllIndexTableById(pd);

            Map<String, String> QX = new HashMap<String, String>();
            QX.put("add", "1");
            QX.put("email", "1");
            QX.put("sms", "1");
            QX.put("del", "1");
            QX.put("cha", "1");
            QX.put("edit", "1");

            mv.addObject("proList", proList);
            mv.addObject("userList", userList);
            mv.addObject("indexTableList", indexTableList);

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
     * 编辑项目
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editProject.do", method = RequestMethod.GET)
    public void editProject(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String id = request.getParameter("proIds");//获取项目id
        String tag = request.getParameter("tag");

        PageData pd = this.getPageData();
        pd.put("id", id);
        TbProject pro = null;
        try {
            pro = this.projectService.getByProjectId(pd);//查询项目信息返回页面
            logger.info(pro.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, pro, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 新增/修改（For 新增/编辑）
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editProject.do", method = RequestMethod.POST)
    public void editProjectPost(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
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
                    String proType = StringUtil.isNotNullOrBlank(request.getParameter("proType"))
                            ? String.valueOf(request.getParameter("proType")) : "";
                    String declareFundsStr = StringUtil.isNotNullOrBlank(request.getParameter("declareFunds"))
                            ? request.getParameter("declareFunds") : "";
                    Date reportorTime = new Date();
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
                    Date reportTime = new Date();
                    System.out.print(Double.valueOf(pd.getString("declareFunds")));
                    pd.put("reportTime", reportTime);
                    pd.put("declareFunds", Double.valueOf(pd.getString("declareFunds")));
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
    public void removeProjects(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String ids = request.getParameter("proIds");
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


    /**
     * 获取已指定的评审用户
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/selectUser.do", method = RequestMethod.GET)
    public void getUserss(HttpServletRequest request, HttpServletResponse response) {
        String projectId = StringUtil.isNotNullOrBlank(request.getParameter("projectId"))
                ? request.getParameter("projectId") : "";
        PageData pd = this.getPageData();
        pd.put("projectId", projectId);
        TbProjectOpinion projectOpinion = null;
        List<String> userList = new ArrayList<String>();
        try {
            projectOpinion = this.projectOpinionService.getProjectOpinion(pd);
            String userId = projectOpinion.getUserId();
            if(null !=userId && !"".equals(userId)){
                String[] userIdArr = userId.split("/");
                for (int i = 0; i < userIdArr.length; i++) {
                    userList.add(userIdArr[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonUtil.writeJsonToResponse(response, userList, JsonUtil.OBJECT_TYPE_LIST);


    }

    /**
     * 获取已有的指标表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editIndexTable.do", method = RequestMethod.GET)
    public void getIndexTable(HttpServletRequest request, HttpServletResponse response) {
        String projectId = StringUtil.isNotNullOrBlank(request.getParameter("param"))
                ? request.getParameter("param") : "";
        PageData pd = this.getPageData();
        pd.put("projectId", projectId);
        TbProjectOpinion projectOpinion;
        List<TbIndexTable> indexTableList = new ArrayList();
        try {
            projectOpinion = projectOpinionService.getProjectOpinion(pd);

            String indexTableId = projectOpinion.getIndexTableId();
            if (null != indexTableId && !"".equals(indexTableId)) {
                String[] indexIdArr = indexTableId.split("/");
                int[] idsArr = new int[indexIdArr.length];
                for (int i = 0; i < indexIdArr.length; i++) {
                    idsArr[i] = Integer.parseInt(indexIdArr[i]);
                }
                indexTableList = indexTableService.queryIndexTableById(idsArr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonUtil.writeJsonToResponse(response, indexTableList, JsonUtil.OBJECT_TYPE_LIST);

    }

    /**
     * 保存表id
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/saveIndexTableId.do", method = RequestMethod.POST)
    public void saveIndexTableId(HttpServletRequest request, HttpServletResponse response) {
        String indexTableArr = request.getParameter("indexTableArr"); // 指标表id
        String projectId = request.getParameter("projectId"); // 项目id
        PageData pd = this.getPageData();
        Result result = new Result();
        try {
            String indexTableId = "";
            if (!("".equals(indexTableArr) || null == indexTableArr || "null".equals(indexTableArr))) {

                List<String> indexTableIdList = JSONArray.parseArray(indexTableArr, String.class);//前端的json数组转成list
                for (int i = 0; i < indexTableIdList.size(); i++) { // 拼接成字符串
                    if (i != indexTableIdList.size() - 1) {
                        indexTableId += indexTableIdList.get(i) + "/";
                    } else {
                        indexTableId += indexTableIdList.get(i);
                    }
                }
                pd.put("indexTableId", indexTableId);

            } else {
                pd.put("indexTableId", indexTableId);
            }

            TbProjectOpinion projectOpinion = projectOpinionService.getProjectOpinion(pd);
            Boolean success;
            if (null != projectOpinion) {
                success = this.projectOpinionService.updateProjectOpinion(pd);
            } else {
                TbProjectOpinion projectOpinionAdd = new TbProjectOpinion();
                projectOpinionAdd.setProjectId(Integer.valueOf(projectId));
                projectOpinionAdd.setIndexTableId(indexTableId);
                Integer res = (Integer) this.projectOpinionService.add(projectOpinionAdd);
                if (res > 0) {
                    success = true;
                } else {
                    success = false;
                }
            }

            if (success) {
                result.setCode("0");
                result.setMessage("更新指标表成功");
                result.setSuccess(true);
            } else {
                result.setCode("1");
                result.setMessage("更新失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonUtil.writeJsonToResponse(response, result, JsonUtil.OBJECT_TYPE_BEAN);


    }

    /**
     * 保存评审用户
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/saveUser.do", method = RequestMethod.POST)
    public void saveUser(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId"); // 指标表id
        String projectId = request.getParameter("projectId"); // 项目id
        PageData pd = this.getPageData();
        Result result = new Result();
        try {
            TbProjectOpinion projectOpinion = projectOpinionService.getProjectOpinion(pd);
            Boolean success;
            if (null != projectOpinion) {
                success = this.projectOpinionService.updateProjectOpinion(pd);
            } else {
                TbProjectOpinion projectOpinionAdd = new TbProjectOpinion();
                projectOpinionAdd.setProjectId(Integer.valueOf(projectId));
                projectOpinionAdd.setUserId(userId);
                Integer res = (Integer) this.projectOpinionService.add(projectOpinionAdd);
                if (res > 0) {
                    success = true;
                } else {
                    success = false;
                }
            }

            if (success) {
                result.setCode("0");
                result.setMessage("更新评审专家成功");
                result.setSuccess(true);
            } else {
                result.setCode("1");
                result.setMessage("更新失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonUtil.writeJsonToResponse(response, result, JsonUtil.OBJECT_TYPE_BEAN);

    }

    /**
     * 获取需当前用户评审的项目
     * @param request
     * @param response
     */
    @RequestMapping(value = "" ,method = RequestMethod.POST)
    public void getProject(HttpServletRequest request,HttpServletResponse response){
        //this.ge

        // 获取当前用户账号
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userInfoSession");
        String userIdd = user.getUserId();



    }
}
