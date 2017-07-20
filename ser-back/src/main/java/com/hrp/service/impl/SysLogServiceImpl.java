package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.SysLog;
import com.hrp.service.SysLogService;
import com.hrp.utils.lang.StringUtil;
import com.hrp.utils.plugins.Page;
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

    @Override
    public List<SysLog> sysLogListPage(Page page) throws Exception {
        return (List<SysLog>) baseDao.findForList("SysLogMapper.sysLogListPage", page);
    }

    @Override
    public SysLog findLogById(String logId) throws Exception {
        if (StringUtil.isBlankOrNull(logId))    return null;
        return (SysLog) baseDao.findForObject("SysLogMapper.findLogById", logId);
    }

}
