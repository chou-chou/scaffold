package com.hrp.utils.log;

import com.hrp.entity.system.SysLog;
import com.hrp.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * SysLogConsumer
 * 日志的保存线程
 * @author KVLT
 * @date 2017-05-31.
 */
@Component
public class SysLogConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SysLogConsumer.class);

    public static final int DEFAULT_BATCH_SIZE = 10;

    private int batchSize = DEFAULT_BATCH_SIZE;

    @Resource
    private SysLogQueue sysLogQueue;

    @Resource
    private SysLogService sysLogService;

    private boolean active = true;
    private Thread thread;

    @PostConstruct
    public void init() {
        thread = new Thread(this);
        thread.start();
    }

    @PreDestroy
    public void close() {
        active = false;
    }

    public void run() {
        while (active) {
            execute();
        }
    }

    public void execute() {
        List<SysLog> sysLogList = new ArrayList<>();

        try {
            int size = 0;

            while (size < batchSize) {
                SysLog sysLog = sysLogQueue.poll();

                if (null == sysLog)     break;

                sysLogList.add(sysLog);
                size ++;
            }

            if (!sysLogList.isEmpty()) {
                logger.info("------------------ 持久化日志到数据库中... --------------------");
                sysLogService.batchSave(sysLogList);  // 持久化日志到数据库
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage(), ex);
        }
    }

}
