package com.hrp.filter;

import com.hrp.utils.SystemValidate;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * SysAuthorityFilter
 * 系统有效性验证。（该过滤器要配置在shiro过滤链的最前端）
 * isAccessAllowed：即是否允许访问，返回true表示允许；
 * onAccessDenied：表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）。
 * @author KVLT
 * @date 2017-03-29.
 */
public class SysAuthorityFilter extends AccessControlFilter {

    protected static final Logger logger = LoggerFactory.getLogger(SysAuthorityFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object obj) throws Exception {

        /**
         * 获取系统有效性信息
         */
        return SystemValidate.validate();  // true - 有效
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.getAndClearSavedRequest(request);  // 清理原先的地址
        logger.info("重定向到authorization.do ... ");
        WebUtils.redirectToSavedRequest(request, response, "anon/authorization.do"); // 重定向到认证失败页面
        //WebUtils.issueRedirect(request, response, "authorization.do");  // 导向认证页面,导向login由于用户已登录，会导致login无效
        return false;
    }
}
