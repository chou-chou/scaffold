package com.hrp.controller.back;

import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.Menu;
import com.hrp.entity.system.TreeNode;
import com.hrp.pojo.Result;
import com.hrp.service.MenuService;
import com.hrp.utils.*;
import com.hrp.utils.lang.StringUtil;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MenuController
 * 菜单模块
 * @author KVLT
 * @date 2017-03-23.
 */
@Controller
@RequestMapping(value = "/b/menu")
public class MenuController extends BaseController {

    // menu模块基础路径
    private final static String BASE_PATH = "back/system/menu/";  // --> WEB-INF/views/back/system/menu/


    String menuUrl = "menu.do";  // 菜单地址（权限用）

    @Resource(name = "menuService")
    private MenuService menuService;

    /**
     * 菜单主页面
     */
    @RequestMapping(method = RequestMethod.GET, value = "/main.do")
    private ModelAndView main() {
        ModelAndView mv = this.getModelAndView(BASE_PATH + "menu_main");
        List<Menu> menuList = new ArrayList();

        try {
            menuList = this.menuService.listAllMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mv.addObject("menuList", menuList);
        return mv;
    }

    /**
     * 请求新增菜单页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd() throws Exception {
        ModelAndView mv = this.getModelAndView();
        try{
            PageData pd = new PageData();
            pd = this.getPageData();
            String MENU_ID = (null == pd.get("MENU_ID") || "".equals(pd.get("MENU_ID").toString()))?"0":pd.get("MENU_ID").toString();//接收传过来的上级菜单ID,如果上级为顶级就取值“0”
            pd.put("MENU_ID",MENU_ID);
            mv.addObject("pds", menuService.getMenuById(pd));	//传入父菜单所有信息
            mv.addObject("MENU_ID", MENU_ID);					//传入菜单ID，作为子菜单的父菜单ID用
            mv.addObject("MSG", "add");							//执行状态 add 为添加
            mv.setViewName(BASE_PATH + "menu_edit");
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 保存菜单信息
     * @param menu
     * @return
     */
    @RequestMapping(value="/add")
    public ModelAndView add(Menu menu)throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"保存菜单");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            menu.setMenuId(Integer.parseInt(menuService.findMaxId(pd).get("MID").toString())+1);
            menu.setIcon("menu-icon fa fa-leaf black");//默认菜单图标
            menuService.saveMenu(menu); //保存菜单
        } catch(Exception e){
            logger.error(e.toString(), e);
            mv.addObject("msg","failed");
        }
        mv.setViewName("redirect:/menu.do?MSG=change&MENU_ID="+menu.getSupId()); //保存成功跳转到列表页面
        return mv;
    }

    /**
     * 删除菜单
     * @param MENU_ID
     */
    @RequestMapping(value="/delete")
    @ResponseBody
    public Object delete(@RequestParam String MENU_ID)throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"删除菜单");
        Map<String,String> map = new HashMap<String,String>();
        String errInfo = "";
        PageData pd=new PageData();
        pd.put("MENU_ID",MENU_ID);
        try{
            if(menuService.listSubMenuByParentId(MENU_ID).size() > 0){//判断是否有子菜单，是：不允许删除
                errInfo = "false";
            }else{
                menuService.deleteMenuById(pd);
                errInfo = "success";
            }
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 请求编辑菜单页面
     * @param
     * @return
     */
    @RequestMapping(value="/toEdit")
    public ModelAndView toEdit(String id)throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            pd.put("MENU_ID",id);				//接收过来的要修改的ID
            pd = menuService.getMenuById(pd);	//读取此ID的菜单数据
            mv.addObject("pd", pd);				//放入视图容器
            pd.put("MENU_ID",pd.get("PARENT_ID").toString());			//用作读取父菜单信息
            mv.addObject("pds", menuService.getMenuById(pd));			//传入父菜单所有信息
            mv.addObject("MENU_ID", pd.get("PARENT_ID").toString());	//传入父菜单ID，作为子菜单的父菜单ID用
            mv.addObject("MSG", "edit");
            pd.put("MENU_ID",id);			//复原本菜单ID
            mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
            mv.setViewName(BASE_PATH + "menu_edit");
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 保存编辑
     * @param
     * @return
     */
    @RequestMapping(value="/edit")
    public ModelAndView edit(Menu menu)throws Exception{
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"修改菜单");
        ModelAndView mv = this.getModelAndView();
        try{
            menuService.updateMenu(menu);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        mv.setViewName("redirect:/menu.do?MSG=change&MENU_ID="+menu.getSupId()); //保存成功跳转到列表页面
        return mv;
    }

    /**
     * 请求编辑菜单图标页面
     * @param
     * @return
     */
    @RequestMapping(value="/toEditicon")
    public ModelAndView toEditicon(String MENU_ID)throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            pd.put("MENU_ID",MENU_ID);
            mv.addObject("pd", pd);
            mv.setViewName(BASE_PATH + "menu_icon");
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 保存菜单图标
     * @param
     * @return
     */
    @RequestMapping(value="/editicon")
    public ModelAndView editicon()throws Exception {
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
        logBefore(logger, Jurisdiction.getUsername()+"修改菜单图标");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            pd = menuService.editicon(pd);
            mv.addObject("msg","success");
        } catch(Exception e){
            logger.error(e.toString(), e);
            mv.addObject("msg","failed");
        }
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 显示菜单列表ztree(菜单管理)
     * @param model
     * @return
     */
    @RequestMapping(value="/listAllMenu")
    public ModelAndView listAllMenu(Model model, String MENU_ID)throws Exception{
        ModelAndView mv = this.getModelAndView();
        try{
            JSONArray arr = JSONArray.fromObject(menuService.listAllMenu("0"));
            String json = arr.toString();
            json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked").replaceAll("MENU_URL", "url");
            model.addAttribute("zTreeNodes", json);
            mv.addObject("MENU_ID",MENU_ID);
            mv.setViewName(BASE_PATH + "menu_ztree");
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 返回给前菜单列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMenuTree.do", method = RequestMethod.POST)
    public void getMenuTree(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据

        String menuInfo = request.getParameter("menuInfo");

        PageData pd = new PageData();
        if (StringUtil.isNotBlank(menuInfo))     pd.put("menuInfo", menuInfo);

        List<TreeNode> menuList = new ArrayList();

        try {
            menuList = this.menuService.selectMenuCascade(pd);
        } catch (Exception e) {
            logger.info("获取菜单数据列表出错...");
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, menuList, JsonUtil.OBJECT_TYPE_LIST);  // 转换为标准json格式
    }

    /**
     * 编辑页面（For 新增/编辑）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editMenu.do", method = RequestMethod.GET)
    public void editMenu(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String menuId = request.getParameter("menuId");
        String tag = request.getParameter("tag");
        PageData pd = new PageData();
        pd.put("menuId", menuId);
        Menu menu = null;

        try {
            menu = this.menuService.getMenuByIds(pd);
            logger.info(menu.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, menu, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 保存新增/修改（For 新增/编辑）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editMenu.do", method = RequestMethod.POST)
    public void editMenuPost(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String tag = request.getParameter("tag");
        if ("".equals(tag)){
            tag="ADD";
        }
        Result rc = new Result();
        PageData pd = this.getPageData();

        switch (tag) {
            case "ADD": // 新增
                try {
                    String menuTag = StringUtil.isNotNullOrBlank(request.getParameter("menuTag"))
                            ? request.getParameter("menuTag") : "";
                    String menuName = StringUtil.isNotNullOrBlank(request.getParameter("menuName"))
                            ? request.getParameter("menuName") : "";
                    String menuUrl = StringUtil.isNotNullOrBlank(request.getParameter("menuUrl"))
                            ? request.getParameter("menuUrl") : "";
                    String supId = StringUtil.isNotNullOrBlank(request.getParameter("supId"))
                            ? request.getParameter("supId") : "";   // 父级Code
                    String icon = StringUtil.isNotNullOrBlank(request.getParameter("icon"))
                            ? request.getParameter("icon") : "";
                    String remark = StringUtil.isNotNullOrBlank(request.getParameter("remark"))
                            ? request.getParameter("remark") : "";
                    String isLeafStr = StringUtil.isNotNullOrBlank(request.getParameter("isLeaf"))
                            ? request.getParameter("isLeaf") : "";
                    String sequence = StringUtil.isNotNullOrBlank(request.getParameter("sequence"))
                            ? request.getParameter("sequence") : "";
                    Boolean isLeaf=false;
                    if("1".equals(isLeafStr)){
                        isLeaf = true;
                    }

                    Menu menu = new Menu();
                    menu.setMenuTag(menuTag);
                    menu.setMenuName(menuName);
                    menu.setMenuUrl(menuUrl);
                    menu.setSupId(Integer.valueOf(supId));
                    menu.setIcon(icon);
                    menu.setRemark(remark);
                    menu.setLeaf(isLeaf);
                    menu.setEnabled(true);//暂时默认激活
                    menu.setSequence(Integer.valueOf(sequence));
                    Integer res = (Integer) this.menuService.saveMenu(menu);  // mybatis insert后Menu会获取实体数据，包括新增的id
                    Menu supMenu = this.menuService.getSupMenu(Integer.valueOf(supId));  // 有可能是根级root -> -1
                    menu.setParentMenu(supMenu);  // 父级bean

                    if (res > 0) {
                        rc.setCode(Constant.EXECUTE_SUCCESS);
                        rc.setMessage("新增菜单成功");
                        rc.setData(menu); // 返回部门数据（包括父级）
                        rc.setSuccess(true);
                    } else {
                        rc.setCode(Constant.EXECUTE_FAILED);
                        rc.setMessage("新增失败");
                        rc.setSuccess(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "EDIT": // 编辑
                try {
                    Boolean success = this.menuService.updateMenuPd(pd);
                    if (success) {
                        rc.setCode(Constant.EXECUTE_SUCCESS);
                        rc.setMessage("更新菜单成功");
                        rc.setSuccess(true);
                        rc.setData(request.getParameter("menuId"));
                    } else {
                        rc.setCode(Constant.EXECUTE_FAILED);
                        rc.setMessage("更新失败");
                        rc.setSuccess(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default: break;
        }
        //
        JsonUtil.writeJsonToResponse(response, rc, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 删除
     * @param request
     * @param response
     */
    @RequestMapping(value = "/removeMenu.do", method = RequestMethod.POST)
    public void removeDictionary(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String menuId = request.getParameter("menuId");

        Result rc = new Result();
        PageData pd = this.getPageData();
        pd.put("menuId",menuId);
        try {
            Boolean success = this.menuService.deleteMenuById(pd);
            if (success) {
                rc.setCode("0");
                rc.setMessage("删除菜单成功");
                rc.setData(menuId);
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
