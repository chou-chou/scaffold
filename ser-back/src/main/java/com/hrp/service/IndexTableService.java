package com.hrp.service;

import com.hrp.entity.business.TbIndexTable;
import com.hrp.entity.system.TbTeam;
import com.hrp.entity.system.UserTeam;
import com.hrp.utils.PageData;

import java.util.List;

/**
 * TeamService
 * 指标表接口
 * @author KVLT
 *
 */
public interface IndexTableService {

    /**
     * 列出所有指标表
     * @param pd
     * @return
     * @throws Exception
     */
    public List<TbIndexTable> listAllIndexTableById(PageData pd) throws Exception;

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

    public Object add(TbIndexTable indexTable) throws Exception;
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
    public boolean deleteIndexTableById(int[] teamId) throws Exception;


    public TbIndexTable getByIndexTableId(PageData pd) throws Exception ;

    /**
     * 更新项目
     * @param pd
     * @return
     * @throws Exception
     *
     */
    public boolean updateIndexTable(PageData pd) throws Exception;

    /**
     * 根据团队id来查询该团队的成员数据
     * @param id
     * @return
     * @throws Exception
     */
    public  List<UserTeam> getIndexByTableId(int id) throws Exception;

    public  Object insertUserTeamBstch(List<UserTeam> userTeam) throws Exception;

    public  Object batchDeleteUserTeam(List<Integer> id) throws Exception;

    public Object deleteUserTeam(Integer id) throws Exception;

    public List<TbIndexTable> queryIndexTableById(int[] ids) throws Exception;




}
