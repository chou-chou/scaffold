package com.hrp.dao;

import com.hrp.entity.system.SysLog;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SysLogMapperTest
 *
 * @author KVLT
 * @date 2017-06-01.
 */
public class SysLogMapperTest extends BaseDaoTest {

    @Test
    @Rollback(false)
    public void batchSave() throws Exception {
        List<SysLog> sysLogs = new ArrayList<>();
        SysLog sl = new SysLog();
        sl.setCreateDate(new Date());
        sl.setBeginDate("2017-06-01");
        sl.setHandle("测试");

        sysLogs.add(sl);

        dao.batchSave("SysLogMapper.batchSave", sysLogs);
    }
}
