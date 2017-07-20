package com.hrp.controller.back;

import com.alibaba.fastjson.JSONArray;
import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.business.TbIndexTable;
import com.hrp.entity.business.TbProjectIndex;
import com.hrp.pojo.Result;
import com.hrp.service.IndexTableService;
import com.hrp.service.ProjectIndexService;
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
import java.util.*;

/**
 * RoleController
 *
 * @author KVLT
 */
@Controller
@RequestMapping(value = "/b/indexTable")
public class IndexTableController extends BaseController {

    private final static String BASE_PATH = "back/business/project/";  // --> WEB-INF/views/back/business/project/

    @Resource(name = "indexTableService")
    private IndexTableService indexTableService;
    @Resource(name = "projectIndexService")
    private ProjectIndexService projectIndexService;


    /**
     * 查询所有记录
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list.do")
    @MvcMapping(tag = "indexTable:list", path = BASE_PATH + "indexTable_list", type = MvcMapping.ViewType.JSP)
    public ModelAndView indexTablelist() {
        ModelAndView mv = this.getModelAndView(BASE_PATH + "indexTable_list");
        Page page = this.getPage();
        PageData pd = this.getPageData();

        try {
            String indexListInfo = pd.getString("indexListInfo");
            if (StringUtil.isNotNullOrBlank(indexListInfo)) {
                pd.put("indexListInfo", indexListInfo.trim());
            } else {
                pd.put("indexListInfo", "");
            }
            page.setPd(pd);
            List<TbIndexTable> indexTableList = this.indexTableService.listAllIndexTableById(pd);//表数据
            List<TbProjectIndex> indexList = projectIndexService.listAllProjectsIndexById(pd); // 指标数据
            mv.addObject("indexTableList", indexTableList);
            mv.addObject("indexList", indexList);
            mv.addObject("pd", pd);
            mv.addObject("page", page);
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
    @RequestMapping(value = "/editIndexTable.do", method = RequestMethod.GET)
    public void editTeam(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String id = request.getParameter("indexTableId");//获取表id
        String tag = request.getParameter("tag");

        PageData pd = this.getPageData();
        pd.put("id", id);
        TbIndexTable indexTable = null;
        try {
            indexTable = this.indexTableService.getByIndexTableId(pd);//查询表信息返回页面
            logger.info(indexTable.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, indexTable, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 新增/修改（For 新增/编辑）
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editindexTable.do", method = RequestMethod.POST)
    public void editTeamPost(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String tag = request.getParameter("tag");
        Result rc = new Result();

        PageData pd = this.getPageData();
        switch (tag) {
            case "ADD": // 新增
                try {
                    String indexTableCode = StringUtil.isNotNullOrBlank(request.getParameter("indexTableCode"))
                            ? request.getParameter("indexTableCode") : "";
                    String indexTableName = StringUtil.isNotNullOrBlank(request.getParameter("indexTableName"))
                            ? request.getParameter("indexTableName") : "";
                    String creator = StringUtil.isNotNullOrBlank(request.getParameter("creator"))
                            ? request.getParameter("creator") : "";
                    String remark = StringUtil.isNotNullOrBlank(request.getParameter("remark"))
                            ? request.getParameter("remark") : "";
                    TbIndexTable indexTable = new TbIndexTable();
                    indexTable.setIndexTableCode(indexTableCode);
                    indexTable.setIndexTableName(indexTableName);
                    indexTable.setCreator(creator);
                    indexTable.setRemark(remark);
                    Integer res = (Integer) this.indexTableService.add(indexTable);  // mybatis insert后dictionary会获取实体数据，包括新增的id

                    if (res > 0) {
                        rc.setCode("0");
                        rc.setMessage("新增表成功");
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
                    Boolean success = this.indexTableService.updateIndexTable(pd); // 更新
                    if (success) {
                        rc.setCode("0");
                        rc.setMessage("更新表成功");
                        rc.setSuccess(true);
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
    @RequestMapping(value = "/deleteIndexTable.do", method = RequestMethod.POST)
    public void removeTeam(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("idnexTableIds");
        String[] arr = ids.split(",");
        Result rc = new Result();
        int[] idsArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) { // 页面id转成int数组
            idsArr[i] = Integer.parseInt(arr[i]);
        }
        try {
            Boolean success = this.indexTableService.deleteIndexTableById(idsArr); // 执行删除数据操作
            if (success) {
                rc.setCode("0");
                rc.setMessage("删除表成功");

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
     * 获取团队已有的用户
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editIndexId.do", method = RequestMethod.GET)
    public void getUsers(HttpServletRequest request, HttpServletResponse response) {
        String id = StringUtil.isNotNullOrBlank(request.getParameter("param"))
                ? request.getParameter("param") : "";
        PageData pd = this.getPageData();
        pd.put("id", id);
        TbIndexTable indexTable;
        List<TbProjectIndex> indexList = new ArrayList<>();
        try {
            indexTable = this.indexTableService.getByIndexTableId(pd);//查询表信息返回页面
            String indexId = indexTable.getIndexId();
            if (null != indexId && !"".equals(indexId)) {
                String[] indexIdArr = indexId.split("/");
                int[] idsArr = new int[indexIdArr.length];
                for (int i = 0; i < indexIdArr.length; i++) {
                    idsArr[i] = Integer.parseInt(indexIdArr[i]);
                }
                indexList = projectIndexService.queryProjectIndexById(idsArr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonUtil.writeJsonToResponse(response, indexList, JsonUtil.OBJECT_TYPE_LIST);


    }

    /**
     * 保存团队成员
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/saveIndexId.do", method = RequestMethod.POST)
    public void saveIndex(HttpServletRequest request, HttpServletResponse response) {
        String indexArr = request.getParameter("indexArr"); // 指标id
        PageData pd=this.getPageData();
        Result result = new Result();
        try {
            String indexId = "";
            if (!("".equals(indexArr) || null == indexArr || "null".equals(indexArr))) {

                List<String> memberIdList = JSONArray.parseArray(indexArr, String.class);//前端的json数组转成list
                for (int i = 0; i < memberIdList.size(); i++) { // 拼接成字符串
                    if (i != memberIdList.size() - 1) {
                        indexId += memberIdList.get(i) + "/";
                    } else {
                        indexId += memberIdList.get(i);
                    }
                }
                pd.put("indexId",indexId);

            }else{
                pd.put("indexId",indexId);
            }
            Boolean success = this.indexTableService.updateIndexTable(pd); // 更新数据
            if (success) {
                result.setCode("0");
                result.setMessage("更新指标成功");
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


}
