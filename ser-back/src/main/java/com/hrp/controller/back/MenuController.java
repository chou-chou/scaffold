package com.hrp.controller.back;

import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.Menu;
import com.hrp.service.MenuService;
import com.hrp.utils.AppUtil;
import com.hrp.utils.Jurisdiction;
import com.hrp.utils.PageData;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
    private final static String BASE_PATH = "back/system/menu/";  // --> WEB-INF/views/system/menu/

    String menuUrl = "menu.do";  // 菜单地址（权限用）

    @Resource(name = "menuService")
    private MenuService menuService;

    /**
     * 角色列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list.do")
    private ModelAndView list() {
        ModelAndView mv = this.getModelAndView();

        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String MENU_ID = (null == pd.get("MENU_ID") || "".equals(pd.get("MENU_ID").toString()))?"0":pd.get("MENU_ID").toString();
            List<Menu> menuList = menuService.listAllMenu();
            // mv.addObject("pd", menuService.getMenuById(pd));	//传入父菜单所有信息
            mv.addObject("MENU_ID", MENU_ID);
            mv.addObject("MSG", null == pd.get("MSG")?"list":pd.get("MSG").toString()); //MSG=change 则为编辑或删除后跳转过来的
            mv.addObject("menuList", menuList);
//            mv.addObject("QX", Jurisdiction.getHC());	//按钮权限
            mv.setViewName(BASE_PATH + "menu_list");
        } catch(Exception e){
            logger.error(e.toString(), e);
        }

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
        try{
            if(menuService.listSubMenuByParentId(MENU_ID).size() > 0){//判断是否有子菜单，是：不允许删除
                errInfo = "false";
            }else{
                menuService.deleteMenuById(MENU_ID);
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
            menuService.edit(menu);
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

}
