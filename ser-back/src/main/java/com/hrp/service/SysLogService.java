package com.hrp.service;

import com.hrp.entity.system.SysLog;
import com.hrp.utils.plugins.Page;

import java.util.List;

/**
 * SysLogService
 *
 * @author KVLT
 * @date 2017-05-31.
 */
public interface SysLogService {

    public int add(SysLog sysLog) throws Exception;

    public int batchSave(List<SysLog> sysLogs) throws Exception;

    public List<SysLog> sysLogListPage(Page page) throws Exception;

    public SysLog findLogById(String logId) throws Exception;

}
