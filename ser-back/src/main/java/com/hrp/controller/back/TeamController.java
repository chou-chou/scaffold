package com.hrp.controller.back;

import com.alibaba.fastjson.JSONArray;
import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.TbTeam;
import com.hrp.entity.system.UserTeam;
import com.hrp.pojo.Result;
import com.hrp.service.TeamService;
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
import java.util.*;

/**
 * RoleController
 *
 * @author KVLT
 * @date 2017-03-23.
 */
@Controller
@RequestMapping(value = "/b/team")
public class TeamController extends BaseController {

    // TbProject模块基础路径
    private final static String BASE_PATH = "back/system/team/";  // --> WEB-INF/views/back/business/project/

    @Resource(name = "teamService")
    private TeamService teamService;
    @Resource(name = "userService")
    private UserService userService;

    /**
     * 查询所有记录
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list.do")
    @MvcMapping(tag = "team:list", path = BASE_PATH + "team_list", type = MvcMapping.ViewType.JSP)
    private ModelAndView teamlist() {
        ModelAndView mv = this.getModelAndView();
        Page page = this.getPage();
        PageData pd = this.getPageData();

        try {
            String userInfo = pd.getString("userInfo");
            if (StringUtil.isNotNullOrBlank(userInfo)) {
                pd.put("userInfo", userInfo.trim());
            }
            page.setPd(pd);
            List<TbTeam> teamList = this.teamService.listAllTeamByPId(pd);
            List<PageData> userList = this.userService.listPdPageUser(page);
            Map<String, String> QX = new HashMap<String, String>();
            QX.put("add", "1");
            QX.put("email", "1");
            QX.put("sms", "1");
            QX.put("del", "1");
            QX.put("cha", "1");
            QX.put("edit", "1");

            mv.addObject("teamList", teamList);
            mv.addObject("userList", userList);
            mv.addObject("pd", pd);
            mv.addObject("page", page);
            mv.addObject("QX", QX);
            mv.setViewName(BASE_PATH + "team_list");
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
    @RequestMapping(value = "/editTeam.do", method = RequestMethod.GET)
    public void editTeam(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String teamId = request.getParameter("teamId");//获取团队id
        String tag = request.getParameter("tag");

        PageData pd = this.getPageData();
        pd.put("teamId", teamId);
        TbTeam team = null;
        try {
            team = this.teamService.getByTeamId(pd);//查询项目信息返回页面
            logger.info(team.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonUtil.writeJsonToResponse(response, team, JsonUtil.OBJECT_TYPE_BEAN);
    }

    /**
     * 新增/修改（For 新增/编辑）
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/editTeam.do", method = RequestMethod.POST)
    public void editTeamPost(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String tag = request.getParameter("tag");
        Result rc = new Result();

        PageData pd = this.getPageData();
        switch (tag) {
            case "ADD": // 新增
                try {
                    String teamCode = StringUtil.isNotNullOrBlank(request.getParameter("teamCode"))
                            ? request.getParameter("teamCode") : "";
                    String teamName = StringUtil.isNotNullOrBlank(request.getParameter("teamName"))
                            ? request.getParameter("teamName") : "";
                    String teamType = StringUtil.isNotNullOrBlank(request.getParameter("teamType"))
                            ? request.getParameter("teamType") : "";
                    String remark = StringUtil.isNotNullOrBlank(request.getParameter("remark"))
                            ? request.getParameter("remark") : "";
                    Date createTime = new Date();

                    TbTeam team = new TbTeam();
                    team.setTeamCode(teamCode);
                    team.setTeamName(teamName);
                    team.setTeamType(teamType);
                    team.setCreateTime(createTime);
                    team.setRemark(remark);
                    Integer res = (Integer) this.teamService.add(team);  // mybatis insert后dictionary会获取实体数据，包括新增的id

                    if (res > 0) {
                        rc.setCode("0");
                        rc.setMessage("新增团队成功");
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
                    TbTeam team = new TbTeam();
                    Boolean success = this.teamService.updateTeam(pd);
                    if (success) {
                        rc.setCode("0");
                        rc.setMessage("更新团队成功");
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
    @RequestMapping(value = "/deleteTeam.do", method = RequestMethod.POST)
    public void removeTeam(HttpServletRequest request, HttpServletResponse response) {  // 返回json数据
        String ids = request.getParameter("teamIds");
        String[] arr = ids.split(",");
        Result rc = new Result();
        int[] idsArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            idsArr[i] = Integer.parseInt(arr[i]);
        }
        try {
            Boolean success = this.teamService.deleteTeamById(idsArr);
            if (success) {
                rc.setCode("0");
                rc.setMessage("删除团队成功");

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
    @RequestMapping(value = "/editTeamUser.do", method = RequestMethod.GET)
    public void getUsers(HttpServletRequest request, HttpServletResponse response) {
        String teamId = StringUtil.isNotNullOrBlank(request.getParameter("param"))
                ? request.getParameter("param") : "";
        List<UserTeam> userTeamsList = null;
        try {
            userTeamsList = this.teamService.getUserByTeamId(Integer.valueOf(teamId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonUtil.writeJsonToResponse(response, userTeamsList, JsonUtil.OBJECT_TYPE_LIST);


    }

    /**
     * 保存团队成员
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/saveMember.do", method = RequestMethod.POST)
    public void saveMember(HttpServletRequest request, HttpServletResponse response) {
        String teamId = StringUtil.isNotNullOrBlank(request.getParameter("teamId"))
                ? request.getParameter("teamId") : "0";
        String memberStr = request.getParameter("memberArr");

        Result result = new Result();
        List<UserTeam> userTeamsList = null;
        try {
            if (!("".equals(memberStr) || null == memberStr || "null".equals(memberStr))) {
                List<String> memberIdList = JSONArray.parseArray(memberStr, String.class);//前端传过来的所有成员id
                Iterator iterator = memberIdList.iterator();
                userTeamsList = this.teamService.getUserByTeamId(Integer.valueOf(teamId));//数据库中已有的成员
                List<Integer> removeMemberList = new ArrayList(); //需要移除的成员
                List<UserTeam> insertMemberList = new ArrayList<UserTeam>();//插入新的成员
                String memberId = null;
                Integer userTeamid = null;
                if (userTeamsList.size() > 0) {
                    for (UserTeam userTeams : userTeamsList) {
                        UserTeam userTeam = new UserTeam();
                        userTeam.setTeamId(Integer.valueOf(teamId));
                        memberId = userTeams.getUserId();
                        userTeamid = userTeams.getId();
                        userTeam.setUserId(memberId);
                        boolean flag = false;
                        while (iterator.hasNext()) {
                            if (memberId.equals(iterator.next())) {
                                iterator.remove();//剔除重复的成员
                                break;
                            } else if (!iterator.hasNext()) {
                                flag = true;
                            }
                        }
                        if (flag) {
                            removeMemberList.add(userTeamid);//需要移除的成员
                        }
                    }
                }

                for (String userId : memberIdList) {
                    UserTeam insUserTeam = new UserTeam();
                    insUserTeam.setTeamId(Integer.valueOf(teamId));
                    insUserTeam.setUserId(userId);
                    insertMemberList.add(insUserTeam);//插入新的成员
                }
                Integer resu = 0;
                Integer res = 0;
                if (removeMemberList.size() > 0) {
                    resu = (Integer) this.teamService.batchDeleteUserTeam(removeMemberList);//删除剔除的成员数据
                }
                if (insertMemberList.size() > 0) {
                    res = (Integer) this.teamService.insertUserTeamBstch(insertMemberList);//新增成员插入数据库
                }

                if (0 ==res && 0==resu){
                    result.setCode("0");
                    result.setMessage("数据未发生更改");
                    result.setSuccess(true);
                }
                if (res > 0 || resu > 0) {
                    result.setCode("0");
                    result.setMessage("更新成员成功");
                    result.setSuccess(true);
                } else {
                    result.setCode("1");
                    result.setMessage("更新成员失败");
                }


            } else {
                Integer resu = (Integer) this.teamService.deleteUserTeam(Integer.valueOf(teamId));//删除该团队的所有成员数据
                if (resu > 0) {
                    result.setCode("0");
                    result.setMessage("更新成员成功");
                    result.setSuccess(true);
                } else {
                    result.setCode("1");
                    result.setMessage("更新成员失败");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonUtil.writeJsonToResponse(response, result, JsonUtil.OBJECT_TYPE_BEAN);


    }


}
