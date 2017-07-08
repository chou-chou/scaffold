package com.hrp.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * 日志定时清除。@DisallowConcurrentExecution 保证多个任务间不会同时执行.所以在多任务执行时最好加上
 */
public class RemoveLogTask implements Job {

    //private LogInfoDao logInfoDao;

    /**
     * 这里实现定时任务的方法
     */
    public void execute(final JobExecutionContext context) throws JobExecutionException {
    	System.out.println("开始执行日志清理工作...");
        ApplicationContext appCtx = null;
        try {
            appCtx = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
        } catch (SchedulerException e1) {
            e1.printStackTrace();
        }
        if (appCtx != null) {
           // logInfoDao = appCtx.getBean(LogInfoDao.class);
            System.out.println("日志清理...");
            //logInfoDao.deleteByDate();
        }
    }
}
