package com.hrp.service;

import com.hrp.entity.business.TbProject;
import com.hrp.entity.business.TbProjectIndex;
import com.hrp.utils.PageData;

import java.util.List;

/**
 * ProjectService
 * 项目指标接口类
 * @author KVLT
 * @date 2017-07-07
 */
public interface ProjectIndexService {

    /**
     * 列出所有项目指标
     * @param pd
     * @return
     * @throws Exception
     */
    public List<TbProjectIndex> listAllProjectsIndexById(PageData pd) throws Exception;

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

    public Object add(TbProjectIndex projectIndex) throws Exception;
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
    public boolean deleteProjectIndexById(int[] ID) throws Exception;


    public TbProjectIndex getByProjectIndexId(PageData pd) throws Exception ;

    /**
     * 更新项目
     * @param pd
     * @return
     * @throws Exception
     *
     */
    public boolean updateProjectIndex(PageData pd) throws Exception;

    public  List<TbProjectIndex> queryProjectIndexById(int[] ID) throws Exception;

}
