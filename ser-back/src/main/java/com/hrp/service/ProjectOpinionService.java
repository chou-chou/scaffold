package com.hrp.service;

import com.hrp.entity.business.TbProjectIndex;
import com.hrp.entity.business.TbProjectOpinion;
import com.hrp.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Resource on 2017/7/17.
 */

public interface ProjectOpinionService {

    /**
     * 列出所有项目指标
     * @param pd
     * @return
     * @throws Exception
     */
    public List<TbProjectOpinion> listAllProjectsOpinionById(PageData pd) throws Exception;

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

    public Object add(TbProjectOpinion projectOpinin) throws Exception;
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


    public TbProjectOpinion getProjectOpinion(PageData pd) throws Exception ;

    /**
     * 更新项目
     * @param pd
     * @return
     * @throws Exception
     *
     */
    public boolean updateProjectOpinion(PageData pd) throws Exception;

    public  List<TbProjectIndex> queryProjectIndexById(int[] ID) throws Exception;
}
