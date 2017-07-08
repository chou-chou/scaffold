package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.SysLog;
import com.hrp.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * SysLogServiceImpl
 *
 * @author KVLT
 * @date 2017-05-31.
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {

    @Resource(name = "baseDao")
    private BaseDao baseDao;

    @Override
    public int add(SysLog sysLog) throws Exception {
        return (Integer) baseDao.save("SysLogMapper.saveSysLog", sysLog);
    }

    @Override
    public int batchSave(List<SysLog> sysLogs) throws Exception {
        return (Integer) baseDao.batchSave("SysLogMapper.batchSave", sysLogs);
    }
}
