package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.business.TbProjectIndex;
import com.hrp.entity.business.TbProjectOpinion;
import com.hrp.service.ProjectIndexService;
import com.hrp.service.ProjectOpinionService;
import com.hrp.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * RoleServiceImpl
 *
 * @author KVLT
 * @date 2017-03-23.
 */
@Service("projectOpinionService")
public class ProjectOpinionServiceImpl implements ProjectOpinionService {

    @Resource
    public BaseDao baseDao;

    public Object save(String str, Object obj) throws Exception {
        return null;
    }

    public Object update(String str, Object obj) throws Exception {
        return null;
    }

    public Object delete(String str, Object obj) throws Exception {
        return null;
    }

    public Set<String> getRoleCodeSet(String userName) {
        PageData pd = new PageData();
        try {
            baseDao.findForList("", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TbProjectOpinion> listAllProjectsOpinionById(PageData pd) throws Exception {
        return( List<TbProjectOpinion>)baseDao.findForList("ProjectOpinionMapper.getProjectOpinionByProjectId",pd);
    }

    public PageData findObjectById(PageData pd) throws Exception {
        return null;
    }

    public void add(PageData pd) throws Exception {

    }

    public Object add(TbProjectOpinion projectOpinin) throws Exception {
        return baseDao.save("ProjectOpinionMapper.saveProjectOpinionBean",projectOpinin);
    }

    public void edit(PageData pd) throws Exception {

    }

    public boolean deleteProjectIndexById(int[] ids) throws Exception {
        Integer result = (Integer) baseDao.delete("ProjectIndexMapper.batchDeleteProjectIndex", ids);
        return (result > 0) ? true : false;
    }



    @Override
    public TbProjectOpinion getProjectOpinion(PageData pd) throws Exception {
        return (TbProjectOpinion) baseDao.findForObject("ProjectOpinionMapper.geProjectOpinion", pd);
    }

    @Override
    public boolean updateProjectOpinion(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.update("ProjectOpinionMapper.updateProjectOpinion", pd);
        return (result > 0) ? true : false;
    }

    @Override
    public  List<TbProjectIndex> queryProjectIndexById(int[] ids) throws Exception {
        return( List<TbProjectIndex>) baseDao.findForList("ProjectIndexMapper.queryProjectIndexById", ids);

    }
}
