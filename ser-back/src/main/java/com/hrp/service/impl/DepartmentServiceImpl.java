package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
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
        return null;
    }

}
