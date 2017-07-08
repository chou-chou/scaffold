package com.hrp.service;

import com.hrp.entity.system.TbTeam;
import com.hrp.entity.system.UserTeam;
import com.hrp.utils.PageData;

import java.util.List;

/**
 * TeamService
 * 团队接口类
 * @author KVLT
 * @date 2017-03-23.
 */
public interface TeamService {

    /**
     * 列出所有团队
     * @param pd
     * @return
     * @throws Exception
     */
    public List<TbTeam> listAllTeamByPId(PageData pd) throws Exception;

    /**
     * 通过id查找
     * @param pd
     * @return
     */
    public PageData findObjectById(PageData pd) throws Exception;

    /**
     * 添加团队
     * @param pd
     * @throws Exception
     */
    public void add(PageData pd) throws Exception;

    public Object add(TbTeam team) throws Exception;
    /**
     * 保存修改
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception;

    /**
     * 删除团队
     * @param
     * @throws Exception
     */
    public boolean deleteTeamById(int[] teamId) throws Exception;


    public TbTeam getByTeamId(PageData pd) throws Exception ;

    /**
     * 更新项目
     * @param pd
     * @return
     * @throws Exception
     *
     */
    public boolean updateTeam(PageData pd) throws Exception;

    /**
     * 根据团队id来查询该团队的成员数据
     * @param teamId
     * @return
     * @throws Exception
     */
    public  List<UserTeam> getUserByTeamId(int teamId) throws Exception;

    public  Object insertUserTeamBstch(List<UserTeam> userTeam) throws Exception;

    public  Object batchDeleteUserTeam(List<Integer> id) throws Exception;

    public Object deleteUserTeam(Integer id) throws Exception;




}
