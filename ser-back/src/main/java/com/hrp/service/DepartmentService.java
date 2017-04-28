package com.hrp.service;

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

}
