package com.hrp.dao;

import com.hrp.entity.system.Department;
import com.hrp.utils.PageData;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * DepartmentMapperTest
 *
 * @author KVLT
 * @date 2017-03-15.
 */
public class DepartmentMapperTest extends BaseDaoTest {

    @Test
    public void testDepartment() throws Exception{
        PageData pd = new PageData();
        Department dt = new Department();
        //DEPT_CODE, DEPT_NAME,SUP_ID,FUNC,REMARK,ADDRESS)
        dt.setDeptCode("D1101");
        dt.setDeptName("消化内科");
        dt.setSupId(01);
        dt.setFunction("部门");
        dt.setRemark("测试部门");
        dt.setAddress("测试地址");
        dao.save("DepartmentMapper.saveRelativity",dt);
        dao.findForList("DepartmentMapper.selectDepartment", pd);
    }

}
