package com.hrp.dao;

import com.hrp.entity.system.Button;
import com.hrp.utils.PageData;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

/**
 * RoleMapperTest
 *
 * @author KVLT
 * @date 2017-03-15.
 */
public class RoleMapperTest extends BaseDaoTest {

    @Test
    public void getButtonByRoleId() throws Exception {
        List<Button> list = (List<Button>) dao.findForList("ButtonMapper.getButtonByRoleId", 1);
        for (Button button : list) {
            logger.info(button.toString());
        }
    }

    @Test
    @Rollback(false)
    public void batchSaveRoleBtn() throws Exception {
        List<PageData> pdList = new ArrayList<>();
        Integer roleId = 2;
        Integer[] btnIds = new Integer[]{1,2,3,4,5};

        for (Integer btnId : btnIds) {
            PageData pd = new PageData();
            pd.put("ROLE_ID", roleId);
            pd.put("BTN_ID", btnId);

            pdList.add(pd);
        }

        dao.save("RoleMapper.batchSaveRoleBtn", pdList);
    }

    @Test
    @Rollback(false)
    public void batchDeleteRoleBtn() throws Exception {
        Integer roleId = 2;
        Integer[] btnIds = new Integer[]{1,2,3,4,5};

        pd.put("ROLE_ID", roleId);
        pd.put("array", btnIds);

        dao.delete("RoleMapper.batchDeleteRoleBtn", pd);
    }

}
