package com.hrp.controller;

import com.hrp.entity.system.Dictionary;
import com.hrp.entity.system.TreeNode;
import com.hrp.pojo.ResultCode;
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
@RequestMapping(value = "/dictionary")
public class DictionaryController extends BaseController {

    // menu模块基础路径
    private final static String BASE_PATH = "system/dictionary/";  // --> WEB-INF/views/system/dictionary/

    PageData pd = new PageData();

    @Resource(name = "dictionaryService")
    private DictionaryService dictionaryService;

    /**
     * 字典模块主页面
     */
    @RequestMapping(method = RequestMethod.GET, value = "/main.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = this.getModelAndView("system/dictionary/dictionary_main");

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
        ResultCode rc = new ResultCode();

        pd = this.getPageData();

        switch (tag) {
            case "ADD": // 新增
                try {
                    Boolean success = this.dictionaryService.saveDictionary(pd);
                    if (success) {
                        rc.setCode("0");
                        rc.setMessage("新增成功");
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

        ResultCode rc = new ResultCode();

        pd = this.getPageData();

        try {
            Boolean success = this.dictionaryService.deleteByIds(pd);
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

}