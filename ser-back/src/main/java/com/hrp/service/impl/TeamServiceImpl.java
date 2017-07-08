package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.TbTeam;
import com.hrp.entity.system.UserTeam;
import com.hrp.service.TeamService;
import com.hrp.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Resource on 2017/6/27.
 */
@Service("teamService")
public class TeamServiceImpl implements TeamService {

    @Resource
    public BaseDao baseDao;

    @Override
    public List<TbTeam> listAllTeamByPId(PageData pd) throws Exception {
        return( List<TbTeam>)baseDao.findForList("TeamMapper.listAllTeam",pd);
    }

    @Override
    public PageData findObjectById(PageData pd) throws Exception {
        return null;
    }

    @Override
    public void add(PageData pd) throws Exception {

    }

    @Override
    public Object add(TbTeam team) throws Exception {
        return baseDao.save("TeamMapper.saveTeamBean",team);
    }

    @Override
    public void edit(PageData pd) throws Exception {

    }

    @Override
    public boolean deleteTeamById(int[] teamId) throws Exception {
        Integer result = (Integer) baseDao.delete("TeamMapper.batchDeleteTeam", teamId);
        return (result > 0) ? true : false;
    }

    @Override
    public TbTeam getByTeamId(PageData pd) throws Exception {
        return (TbTeam)baseDao.findForObject("TeamMapper.getByTeamId",pd);
    }

    @Override
    public boolean updateTeam(PageData pd) throws Exception {
        Integer result=(Integer) baseDao.update("TeamMapper.updateTeam",pd);
        return (result > 0) ? true:false;
    }

    @Override
    public List<UserTeam> getUserByTeamId(int teamId) throws Exception {
        return (List<UserTeam>)baseDao.findForList("TeamMapper.getUserByTeamId",teamId);
    }

    @Override
    public Object insertUserTeamBstch(List<UserTeam> userTeam) throws Exception {
        return baseDao.save("TeamMapper.insertUserTeam",userTeam);
    }

    @Override
    public Object batchDeleteUserTeam(List<Integer> id) throws Exception {
        return baseDao.save("TeamMapper.batchDeleteUserTeam",id);
    }

    @Override
    public Object deleteUserTeam(Integer id) throws Exception {
        return baseDao.save("TeamMapper.DeleteUserTeam",id);
    }


}
