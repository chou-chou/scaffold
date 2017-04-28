package com.hrp.dao;

import com.hrp.entity.system.Authority;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * AutorityMapperTest
 *
 * @author KVLT
 * @date 2017-04-17.
 */
public class AutorityMapperTest extends BaseDaoTest {

    @Resource
    private BaseDao baseDao;

    @Test
    public void testAll() throws Exception {
        List<Authority> list = (List<Authority>) baseDao.findForList("AuthorityMapper.listAll", null);
        for (Authority autho : list) {
            System.out.println(autho);
        }
    }
}
