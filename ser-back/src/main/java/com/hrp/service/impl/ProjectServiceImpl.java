package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.business.TbProject;
import com.hrp.service.ProjectService;
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
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

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

    public List<TbProject> listAllProjectsByPId(PageData pd) throws Exception {
        return( List<TbProject>)baseDao.findForList("ProjectMapper.listAllProject",pd);
    }

    public PageData findObjectById(PageData pd) throws Exception {
        return null;
    }

    public void add(PageData pd) throws Exception {

    }

    public Object add(TbProject Project) throws Exception {
        return baseDao.save("ProjectMapper.saveProjectBean",Project);
    }

    public void edit(PageData pd) throws Exception {

    }

    public boolean deleteProjectById(int[] ids) throws Exception {
        Integer result = (Integer) baseDao.delete("RoleMapper.batchDeleteProject", ids);
        return (result > 0) ? true : false;
    }



    @Override
    public TbProject getByProjectId(PageData pd) throws Exception {
        return (TbProject) baseDao.findForObject("ProjectMapper.getByProject", pd);
    }
    @Override
    public boolean updateProject(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.update("ProjectMapper.updateProject", pd);
        return (result > 0) ? true : false;
    }
}
