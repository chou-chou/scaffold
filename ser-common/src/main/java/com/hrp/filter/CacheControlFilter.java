package com.hrp.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CacheControlFilter
 * 禁用代理缓存
 * @author KVLT
 * @date 2017-07-18.
 */
public class CacheControlFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Cache-Control", "max-age=0, private");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
