package com.hrp.filter;

import com.hrp.wrapper.GZipResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * GZipFilter
 * 压缩Filter中需要先判断客户浏览器时候支持GZip自动解压，如果支持，则进行GZIP压缩，否则不压缩。判断的依据是浏览器提供的Header信息
 * @author KVLT
 * @date 2017-07-18.
 */
public class GZipFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response =(HttpServletResponse)res;

        String acceptEncoding =request.getHeader("Accept-Encoding");
        //支持的编码方式

        if(acceptEncoding != null && acceptEncoding.toLowerCase().indexOf("gzip") != -1){
            //如果客户浏览器支持GZIP格式，则使用GZIP压缩数据
            GZipResponseWrapper gzipRes = new GZipResponseWrapper(response);

            chain.doFilter(request, gzipRes);//doFilter,使用自定义的response
            gzipRes.finishResponse();//输出压缩数据

        }else{
            chain.doFilter(request, response);//否则不压缩
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {

    }
}
