package com.hrp.service;

import com.hrp.entity.business.TbProject;
import com.hrp.utils.PageData;

import java.util.List;

/**
 * RoleService
 * 角色接口类
 * @author KVLT
 * @date 2017-03-23.
 */
public interface ProjectService {

    /**
     * 列出所有项目
     * @param pd
     * @return
     * @throws Exception
     */
    public List<TbProject> listAllProjectsByPId(PageData pd) throws Exception;

    /**
     * 通过id查找
     * @param pd
     * @return
     */
    public PageData findObjectById(PageData pd) throws Exception;

    /**
     * 添加项目申请
     * @param pd
     * @throws Exception
     */
    public void add(PageData pd) throws Exception;

    public Object add(TbProject Project) throws Exception;
    /**
     * 保存修改
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception;

    /**
     * 删除项目
     * @param
     * @throws Exception
     */
    public boolean deleteProjectById(int[] ID) throws Exception;


    public TbProject getByProjectId(PageData pd) throws Exception ;

    /**
     * 更新项目
     * @param pd
     * @return
     * @throws Exception
     *
     */
    public boolean updateProject(PageData pd) throws Exception;

}
