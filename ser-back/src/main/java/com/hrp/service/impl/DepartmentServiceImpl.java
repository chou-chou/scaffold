package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.Department;
import com.hrp.entity.system.Dictionary;
import com.hrp.entity.system.TreeNode;
import com.hrp.service.DepartmentService;
import com.hrp.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * DepartmentServiceImpl
 *
 * @author KVLT
 * @date 2017-03-24.
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Resource(name = "baseDao")
    private BaseDao baseDao;

    @Override
    public List<TreeNode> selectDepartmentCascade(PageData pd) throws Exception {
        return (List<TreeNode>) baseDao.findForList("DepartmentMapper.selectDepartmentCascade", pd);
    }

    @Override
    public List<Department> listAllDepartment(PageData pd) throws Exception {
        return (List<Department>) baseDao.findForList("DepartmentMapper.selectDepartment", pd);
    }

    @Override
    public Department getByDeptId(PageData pd) throws Exception {
        return (Department) baseDao.findForObject("DepartmentMapper.getByDeptId", pd);
    }

    @Override
    public Boolean saveDepartment(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.save("DepartmentMapper.saveDepartment", pd);
        return (result > 0) ? true : false;
    }

    @Override
    public Department getBySupDep(String supId) throws Exception {
        return (Department) baseDao.findForObject("DepartmentMapper.getBySupCode", supId);
    }

    @Override
    public Boolean updateDepartment(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.update("DepartmentMapper.updateDepartment", pd);
        return (result > 0) ? true : false;
    }

    @Override
    public Object saveDepartment(Department dep) throws Exception {
        return baseDao.save("DepartmentMapper.saveDepartmentBean", dep);
    }
    @Override
    public Boolean deleteByIds(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.delete("DepartmentMapper.deleteDepartment", pd);
        return (result > 0) ? true : false;
    }
}
