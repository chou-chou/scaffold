package com.hrp.service;

import com.hrp.entity.system.Department;
import com.hrp.entity.system.Dictionary;
import com.hrp.entity.system.TreeNode;
import com.hrp.utils.PageData;

import java.util.List;

/**
 * DepartmentService
 * 部门接口类
 * @author KVLT
 * @date 2017-03-24.
 */
public interface DepartmentService {

    public List<TreeNode> selectDepartmentCascade(PageData pd) throws Exception;

    public List<Department> listAllDepartment(PageData pd) throws Exception;

    public Department getByDeptId(PageData pd) throws Exception;


    /**
     * 新增字典
     * @param pd
     * @return
     * @throws Exception
     */
    public Boolean saveDepartment(PageData pd) throws Exception;

    /**
     * 新增字典
     * @param dep
     * @return
     * @throws Exception
     */
    public Object saveDepartment(Department dep) throws Exception;

    /**
     * 编辑字典
     * @param pd
     * @return
     * @throws Exception
     */
    public Boolean updateDepartment(PageData pd) throws Exception;

    public Department getBySupDep(String  supId) throws Exception;

    public Boolean deleteByIds(PageData pd) throws Exception;


}
