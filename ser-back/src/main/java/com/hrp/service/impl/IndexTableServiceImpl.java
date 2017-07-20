package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.business.TbIndexTable;
import com.hrp.entity.system.TbTeam;
import com.hrp.entity.system.UserTeam;
import com.hrp.service.IndexTableService;
import com.hrp.service.TeamService;
import com.hrp.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Resource on 2017/6/27.
 */
@Service("indexTableService")
public class IndexTableServiceImpl implements IndexTableService {

    @Resource
    public BaseDao baseDao;

    @Override
    public List<TbIndexTable> listAllIndexTableById(PageData pd) throws Exception {
        return( List<TbIndexTable>)baseDao.findForList("IndexTableMapper.listAllIndexTable",pd);
    }

    @Override
    public PageData findObjectById(PageData pd) throws Exception {
        return null;
    }

    @Override
    public void add(PageData pd) throws Exception {

    }

    @Override
    public Object add(TbIndexTable indexTable) throws Exception {
        return baseDao.save("IndexTableMapper.saveIndexTableBean",indexTable);
    }

    @Override
    public void edit(PageData pd) throws Exception {

    }

    @Override
    public boolean deleteIndexTableById(int[] indexTableId) throws Exception {
        Integer result = (Integer) baseDao.delete("IndexTableMapper.batchDeleteIndexTable", indexTableId);
        return (result > 0) ? true : false;
    }

    @Override
    public TbIndexTable getByIndexTableId(PageData pd) throws Exception {
        return (TbIndexTable)baseDao.findForObject("IndexTableMapper.getByIndexTableId",pd);
    }

    @Override
    public boolean updateIndexTable(PageData pd) throws Exception {
        Integer result=(Integer) baseDao.update("IndexTableMapper.updateIndexTable",pd);
        return (result > 0) ? true:false;
    }

    @Override
    public List<UserTeam> getIndexByTableId(int teamId) throws Exception {
        return (List<UserTeam>)baseDao.findForList("IndexTableMapper.getIndexByTableId",teamId);
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
    public List<TbIndexTable> queryIndexTableById(int[] ids) throws Exception {
        return( List<TbIndexTable>) baseDao.findForList("IndexTableMapper.queryIndexTableById", ids);
    }

    @Override
    public Object deleteUserTeam(Integer id) throws Exception {
        return baseDao.save("TeamMapper.DeleteUserTeam",id);
    }


}
