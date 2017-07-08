package com.hrp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * PlatformMappingExceptionResolver
 * 平台异常信息跳转、解析
 *
 * @author KVLT
 * @date 2017-06-19.
 */
public class PlatformMappingExceptionResolver extends SimpleMappingExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(PlatformMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {

        System.out.println("==============异常开始=============");
        ex.printStackTrace();

        String viewName = determineViewName(ex, request);
        // vm方式返回
        if (viewName != null) {
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                    .getHeader("X-Requested-With") != null && request
                    .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
                // 非异步方式返回
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }

                System.out.println("============== 1 异常结束=============");

                // 跳转到提示页面
                return getModelAndView(viewName, ex, request);
            } else {
                // 异步方式返回
                try {
                    PrintWriter writer = response.getWriter();
                    writer.write(ExceptionI18Message.getLocaleMessage(ex.getMessage()));
                    response.setStatus(404, ExceptionI18Message.getLocaleMessage(ex.getMessage()));
                    //将异常栈信息记录到日志中
                    logger.error(getTrace(ex));
                    writer.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("============== 2 异常结束=============");
                // 不进行页面跳转
                return null;
            }
        } else {

            System.out.println("==============异常结束=============");
            return null;
        }
    }

    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
