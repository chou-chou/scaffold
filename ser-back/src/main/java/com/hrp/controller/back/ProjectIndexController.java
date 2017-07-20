package com.hrp.controller.back;

import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.business.TbProjectIndex;
import com.hrp.pojo.Result;
import com.hrp.service.ProjectIndexService;
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
import java.util.Date;
import java.util.List;

/**
 * RoleController
 *
 * @author KVLT.
 */
@Controller
@RequestMapping(value = "/b/projectIndex")
public class ProjectIndexController extends BaseController {

    // 项目指标模块基础路径
    private final static String BASE_PATH = "back/business/project/";  // --> WEB-INF/views/back/business/project/

    @Resource(name = "projectIndexService")
    private ProjectIndexService projectIndexService;

    /**
     * 查询所有指标记录
     */
    @RequestMapping(method = RequestMethod.GET, value = "/main.do")
    @MvcMapping(tag = "projectIndex:main", path = BASE_PATH + "projectIndex_main", type = MvcMapping.ViewType.JSP)
    private ModelAndView list() {
        ModelAndView mv = this.getModelAndView();
        Page page = this.getPage();
        PageData pd = this.getPageData();

        try {
            String indexInfo = pd.getString("indexInfo");
            if (StringUtil.isNotNullOrBlank(indexInfo)) {
                pd.put("indexInfo", indexInfo.trim());
            }
            page.setPd(pd);
            List<TbProjectIndex> indexList = this.projectIndexService.listAllProjectsIndexById(pd);


            mv.addObject("indexList", indexList);
            mv.addObject("pd", pd);
            mv.addObject("page", page);
            mv.setViewName(BASE_PATH + "projectIndex_main");
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
    @RequestMapping(value = "/editProjectIndex.do", method = RequestMethod.GET)
    public void editProject(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String id = request.getParameter("indexId");//获取项目id
        PageData pd = this.getPageData();
        pd.put("id", id);
        TbProjectIndex index = null;
        try {
            index = this.projectIndexService.getByProjectIndexId(pd);//查询项目信息返回页面
            logger.info(index.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, index, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 新增/修改（For 新增/编辑）
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editProjectIndex.do", method = RequestMethod.POST)
    public void editProjectPost(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String tag = request.getParameter("tag");
        Result rc = new Result();

        PageData pd = this.getPageData();
        switch (tag) {
            case "ADD": // 新增
                try {
                    String indexCode = StringUtil.isNotNullOrBlank(request.getParameter("indexCode"))
                            ? request.getParameter("indexCode") : "";
                    String indexName = StringUtil.isNotNullOrBlank(request.getParameter("indexName"))
                            ? request.getParameter("indexName") : "";
                    String indexType = StringUtil.isNotNullOrBlank(request.getParameter("indexType"))
                            ? request.getParameter("indexType") : "";
                    String indexScore = StringUtil.isNotNullOrBlank(request.getParameter("indexScore"))
                            ? String.valueOf(request.getParameter("indexScore")) : "";
                    String remark = StringUtil.isNotNullOrBlank(request.getParameter("remark"))
                            ? request.getParameter("remark") : "";
                    TbProjectIndex index = new TbProjectIndex();
                    index.setIndexCode(indexCode);
                    index.setIndexName(indexName);
                    index.setIndexType(indexType);
                    index.setIndexScore(indexScore);
                    index.setRemark(remark);
                    Integer res = (Integer) this.projectIndexService.add(index);  // mybatis insert后dictionary会获取实体数据，包括新增的id

                    if (res > 0) {
                        rc.setCode("0");
                        rc.setMessage("新增指标成功");
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
                    Boolean success = this.projectIndexService.updateProjectIndex(pd);
                    if (success) {
                        rc.setCode("0");
                        rc.setMessage("更新指标成功");
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
    @RequestMapping(value = "/deleteProjectIndexs.do", method = RequestMethod.POST)
    public void removeProjects(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String ids = request.getParameter("indexIds");
        String[] arr = ids.split(",");
        Result rc = new Result();
        int[] idsArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            idsArr[i] = Integer.parseInt(arr[i]);
        }
        try {
            Boolean success = this.projectIndexService.deleteProjectIndexById(idsArr);
            if (success) {
                rc.setCode("0");
                rc.setMessage("删除指标成功");

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
