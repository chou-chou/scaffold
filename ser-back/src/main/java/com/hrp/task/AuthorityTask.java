package com.hrp.task;

import com.hrp.utils.Constant;
import com.hrp.utils.SystemValidate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;

/**
 *  系统项目有效（是否可用/过期）验证
 */
public class AuthorityTask implements Job {

    protected static final Logger logger = LoggerFactory.getLogger(AuthorityTask.class);

    /**
     * 这里实现定时任务的方法
     */
    public void execute(final JobExecutionContext context) throws JobExecutionException {
    	logger.info("开始验证系统有效性...");

        boolean flag = SystemValidate.validate();
        ServletContext ctx =  ContextLoader.getCurrentWebApplicationContext().getServletContext();

        if (flag)
            ctx.setAttribute(Constant.SYS_ENABLED, true);
        else {
            ctx.setAttribute(Constant.SYS_ENABLED, false);
            logger.warn("系统过期... 请联系管理员！");
        }

    }
}
