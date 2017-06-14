package com.hrp.utils.log;

import com.hrp.entity.system.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * SysLogQueue
 * 存放日志的队列
 * @author KVLT
 * @date 2017-05-31.
 */
@Component
public class SysLogQueue {

    private static final Logger logger = LoggerFactory.getLogger(SysLogQueue.class);

    // 队列大小
    public static final int QUEUE_MAX_SIZE = 100;

    // 阻塞队列
    private BlockingQueue<SysLog> logQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    /**
     * 日志入队
     * @param logItem
     */
    public void add(SysLog logItem) {
        logger.info("日志入队: " + logItem.toString());
        logQueue.add(logItem);
    }

    /**
     * 日志出队
     * @return
     * @throws InterruptedException
     */
    public SysLog poll() throws InterruptedException {
        return logQueue.poll(1, TimeUnit.SECONDS);
    }

    /**
     * 获取队列大小
     * @return
     */
    public int size() {
        return this.logQueue.size();
    }
}
