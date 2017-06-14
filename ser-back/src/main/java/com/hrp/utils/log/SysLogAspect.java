package com.hrp.utils.log;

import com.hrp.entity.system.SysLog;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SysLogAspect
 * 日志切面
 *
 * @author KVLT
 * @date 2017-05-31.
 */
@Component
@Aspect
@Order(-1)
public class SysLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private SysLogQueue sysLogQueue;

    ////////////////////////////////// 定义切面 ///////////////////////////

    /**
     * 用户登录切入点
     */
    @Pointcut("execution(* com.hrp.service.impl.UserServiceImpl.getUserByNameAndPwd(..)) " +
            "|| execution(* com.hrp.service.impl.UserServiceImpl.getUserByLoginName(..))")
    public void loginCell() {
    }

    /**
     * 用户登出切入点
     */
    @Pointcut("execution(* com.hrp.service.impl.UserServiceImpl.logout(..))")
    public void logoutCell() {
    }

    /**
     * 新增切入点
     */
    @Pointcut("(execution(* com.hrp.service.impl.*.save*(..)) " +
            " || execution(* com.hrp.service.impl.*.insert*(..)))" +
            " && !bean(sysLogService)")
    public void inseretCell() {
    }

    /**
     * 更新切入点
     */
    @Pointcut("execution(* com.hrp.service.impl.*.edit*(..))" +
            " || execution(* com.hrp.service.impl.*.update*(..))")
    public void updateCell() {
    }

    /**
     * 删除切入点
     */
    @Pointcut("execution(* com.hrp.service.impl.*.delete*(..))")
    public void deleteCell() {
    }


    /**
     * 用户登录切入点
     *
     * @param joinPoint
     * @param rtv
     */
    @AfterReturning(value = "loginCell()", returning = "rtv")
    public void loginLog(JoinPoint joinPoint, Object rtv) {
        logger.info("登陆操作!");
        autoFinish(joinPoint, "登陆系统");
    }

    /**
     * 退出登录切入点
     */
    @Before(value = "logoutCell()")
    public void logoutLog() {
        autoFinish(null, "退出系统");
    }

    /**
     * 添加操作日志(后置通知)
     *
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    @AfterReturning(value = "inseretCell()", returning = "rtv")
    public void insertLog(JoinPoint joinPoint, Object rtv) throws Throwable {
        autoFinish(joinPoint, "新增操作");
    }

    /**
     * 修改操作日志(后置通知)
     *
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    @AfterReturning(value = "updateCell()", returning = "rtv")
    public void updateLog(JoinPoint joinPoint, Object rtv) throws Throwable {
        autoFinish(joinPoint, "更新操作");
    }

    /**
     * 删除操作日志(后置通知)
     *
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    @AfterReturning(value = "deleteCell()", returning = "rtv")
    public void deleteLog(JoinPoint joinPoint, Object rtv) throws Throwable {
        autoFinish(joinPoint, "删除操作");
    }

    //////////////////////////////////// 方法区 ///////////////////////////////////////

    /**
     * 使用Java反射来获取被拦截方法(insert、update)的参数值，
     * 将参数值拼接为操作内容
     *
     * @param args
     * @param mName
     * @return
     */
    private String optionContent(Object[] args, String mName) {
        if (args == null) {
            return null;
        }
        StringBuffer rs = new StringBuffer();
        rs.append(mName);
        String className = null;
        int index = 1;
        //遍历参数对象
        for (Object info : args) {
            //获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数" + index + "，类型:" + className + "，值:");
            //获取对象的所有方法
            Method[] methods = info.getClass().getDeclaredMethods();
            // 遍历方法，判断get方法
            for (Method method : methods) {
                String methodName = method.getName();
                // 判断是不是get方法
                if (methodName.indexOf("get") == -1) {//不是get方法
                    continue;//不处理
                }
                Object rsValue = null;
                try {
                    // 调用get方法，获取返回值
                    rsValue = method.invoke(info);
                } catch (Exception e) {
                    continue;
                }
                //将值加入内容中
                rs.append("(" + methodName + ":" + rsValue + ")");
            }
            rs.append("]");
            index++;
        }
        return rs.toString();
    }

    /**
     * 填充
     *
     * @param joinPoint
     * @param handle
     * @return
     */
    private void autoFinish(JoinPoint joinPoint, String handle) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        logger.info(" --------------- 填充日志数据 -------------");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SysLog sysLog = new SysLog();
        sysLog.setUsername(username == null ? "失去帐号信息" : username);
        sysLog.setCreateDate(new Date());
        sysLog.setIp(getIpAddr(request));
        if (joinPoint != null) {
            sysLog.setUrl(joinPoint.getSignature().toString());
            //获取方法名
            String methodName = joinPoint.getSignature().getName();
            //获取操作内容
            String opContent = optionContent(joinPoint.getArgs(), methodName);
            sysLog.setMethod(methodName);
            sysLog.setParameter(opContent);
        }
        sysLog.setHandle(handle);

        try {
            // 插入日志到消息队列
            sysLogQueue.add(sysLog);
        } catch (Exception e) {

        }
    }

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     * @throws Exception
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
