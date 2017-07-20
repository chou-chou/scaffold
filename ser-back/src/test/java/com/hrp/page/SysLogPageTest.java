package com.hrp.page;

import com.hrp.dao.BaseDaoTest;
import com.hrp.entity.system.SysLog;
import org.junit.Test;

import java.util.List;

/**
 * SysLogPageTest
 *
 * @author KVLT
 * @date 2017-07-18.
 */
public class SysLogPageTest extends BaseDaoTest {

    @Test
    public void sysLogListPage() throws Exception {
        page.setShowCount(10);
        page.setOrderColumn("LOG_ID");
        page.setCurrentPage(1);

        List<SysLog> list = (List<SysLog>)dao.findForList("SysLogMapper.sysLogListPage", page);
        for (SysLog sysLog : list) {
            logger.info(sysLog.toString());
        }
    }
}
