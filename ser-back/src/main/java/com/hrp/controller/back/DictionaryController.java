package com.hrp.controller.back;

import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.Dictionary;
import com.hrp.entity.system.TreeNode;
import com.hrp.pojo.Result;
import com.hrp.service.DictionaryService;
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
 * DictionaryController
 * 字典模块
 * @author KVLT
 * @date 2017-04-13.
 */
@Controller
@RequestMapping(value = "/b/dictionary")
public class DictionaryController extends BaseController {

    // menu模块基础路径
    private final static String BASE_PATH = "back/system/dictionary/";  // --> WEB-INF/views/back/system/dictionary/

    PageData pd = new PageData();

    @Resource(name = "dictionaryService")
    private DictionaryService dictionaryService;

    /**
     * 字典模块主页面
     */
    @RequestMapping(method = RequestMethod.GET, value = "/main.do")
    @MvcMapping(url = "/b/dictionary/main.do", path = BASE_PATH + "dicList", type = MvcMapping.ViewType.JSP)
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = this.getModelAndView(BASE_PATH + "dictionary_main");

        List<Dictionary> dicList = new ArrayList();

        try {
            dicList = this.dictionaryService.listAllDictionary(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mv.addObject("dicList", dicList);
        return mv;
    }

    /**
     * 返回给前段字典列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDicTree.do", method = RequestMethod.POST)
    public void getDicTree(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据

        String dicInfo = request.getParameter("dicInfo");

        PageData pd = new PageData();
        if (StringUtil.isNotBlank(dicInfo))     pd.put("dicInfo", dicInfo);

        List<TreeNode> dicList = new ArrayList();

        try {
            dicList = this.dictionaryService.selectDictionaryCascade(pd);
        } catch (Exception e) {
            logger.info("获取字典数据列表出错...");
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, dicList, JsonUtil.OBJECT_TYPE_LIST);  // 转换为标准json格式
    }

    /**
     * 编辑页面（For 新增/编辑）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editDictionary.do", method = RequestMethod.GET)
    public void editDictionary(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String dicId = request.getParameter("dicId");
        String tag = request.getParameter("tag");

        pd.put("dicId", dicId);
        Dictionary dic = null;

        try {
            dic = this.dictionaryService.getByDicId(pd);
            logger.info(dic.toString());
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
    @RequestMapping(value = "/editDictionary.do", method = RequestMethod.POST)
    public void editDictionaryPost(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String tag = request.getParameter("tag");
        Result rc = new Result();

        pd = this.getPageData();

        switch (tag) {
            case "ADD": // 新增
                try {
                    String entryCode = StringUtil.isNotNullOrBlank(request.getParameter("entryCode"))
                            ? request.getParameter("entryCode") : "";
                    String entryValue = StringUtil.isNotNullOrBlank(request.getParameter("entryValue"))
                            ? request.getParameter("entryValue") : "";
                    String supCode = StringUtil.isNotNullOrBlank(request.getParameter("supCode"))
                            ? request.getParameter("supCode") : "";   // 父级Code
                    String sequence = StringUtil.isNotNullOrBlank(request.getParameter("sequence"))
                            ? request.getParameter("sequence") : "";
                    String enabled = StringUtil.isNotNullOrBlank(request.getParameter("enabled"))
                            ? String.valueOf(request.getParameter("enabled")) : "";
                    String remark = StringUtil.isNotNullOrBlank(request.getParameter("remark"))
                            ? request.getParameter("remark") : "";

                    Dictionary dictionary = new Dictionary();
                    dictionary.setSupCode(supCode);
                    dictionary.setEntryCode(entryCode);
                    dictionary.setEntryValue(entryValue);
                    if (StringUtil.isNotNullOrBlank(sequence))
                        dictionary.setSequence(Integer.parseInt(sequence));
                    dictionary.setEnabled("1".equals(enabled));
                    dictionary.setRemark(remark);

                    Integer res = (Integer) this.dictionaryService.saveDictionary(dictionary);  // mybatis insert后dictionary会获取实体数据，包括新增的id
                    Dictionary supDic = this.dictionaryService.getBySupCode(supCode);  // 有可能是根级root -> -1

                    dictionary.setSupDic(supDic);  // 父级bean
                    if (res > 0) {
                        rc.setCode("0");
                        rc.setMessage("新增成功");
                        rc.setData(dictionary); // 返回字典数据（包括父级）
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
                    Boolean success = this.dictionaryService.updateDictionary(pd);
                    if (success) {
                        rc.setCode("0");
                        rc.setMessage("更新成功");
                        rc.setSuccess(true);
                        rc.setData(request.getParameter("dicId"));
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
    @RequestMapping(value = "/removeDictionary.do", method = RequestMethod.POST)
    public void removeDictionary(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String dicId = request.getParameter("dicId");

        Result rc = new Result();
        pd = this.getPageData();
        pd.put("ids", new String[]{dicId});

        try {
            Boolean success = this.dictionaryService.deleteByIds(pd);
            if (success) {
                rc.setCode("0");
                rc.setMessage("删除成功");
                rc.setData(dicId);
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