package com.hrp.controller.common;

import com.google.common.collect.Lists;
import com.hrp.annotation.MvcDocument;
import com.hrp.pojo.RequestMethodParameter;
import com.hrp.pojo.RequestToMethodItem;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * MappingController
 * spring mvc 获取所有的controller和url映射关系
 * @author KVLT
 * @date 2017-05-16.
 */
@Controller
@RequestMapping(value = "/c/system/")
public class SystemController {

    private static final String BASE_PATH = "back/system/sysInfo/";

    @Autowired
    private RequestMappingHandlerConfig requestMappingHandlerConfig;

    private static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 查看项目所有URL对应的Controller和方法
     * @return
     */
    //@MvcDocument(info = "SpringMvc接口列表", author = "kv", status = MvcDocument.Status.done)
    @RequestMapping(value = "mapping.do", method = RequestMethod.GET)
    public ModelAndView mapping() {
        List<RequestToMethodItem> requestToMethodItems = getActionList(requestMappingHandlerConfig);
        return new ModelAndView(BASE_PATH + "mapping").addObject("actionList", requestToMethodItems);
    }

    @RequestMapping(value = "mappingList.do", method = RequestMethod.GET)
    @MvcDocument(info = "SpringMvc接口列表", author = "kv", status = MvcDocument.Status.done)
    public @ResponseBody List<RequestToMethodItem> mappingList() {
        return getActionList(requestMappingHandlerConfig);
    }

    /**
     * 查看项目所有URL对应的Controller和方法
     * @return
     */
    @RequestMapping(value = "mapping1.do", method = RequestMethod.GET)
    public ModelAndView actionList(HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();
        if (null == servletContext) {
            return null;
        }
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        // 请求url和处理方法的映射
        List<RequestToMethodItem> requestToMethodItems = new ArrayList<RequestToMethodItem>();
        // 获取所有的RequestMapping
        Map<String, HandlerMapping> allRequestMapping = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
                HandlerMapping.class, true, false);

        for (HandlerMapping handlerMapping : allRequestMapping.values()) {
            // 只需要RequestHandlerMapping中的URL映射
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();

                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethodMap.entrySet()) {
                    RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                    HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();

                    RequestMethodsRequestCondition methodsRequestCondition = requestMappingInfo.getMethodsCondition();
                    String requestType = "";
                    for (RequestMethod requestMethod : methodsRequestCondition.getMethods()) {  // 取第一个
                        requestType = requestMethod.name();
                    }

                    PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                    String requestUrl = "";
                    for (String url : patternsCondition.getPatterns()) {
                        requestUrl = url;
                    }

                    String className = mappingInfoValue.getMethod().getDeclaringClass().getName();  // 类名
                    String controllerName = mappingInfoValue.getBeanType().toString();  //
                    String requestMethodName = mappingInfoValue.getMethod().getName();
                    Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();
                    RequestToMethodItem item = new RequestToMethodItem(requestUrl, requestType, controllerName, requestMethodName);
                    requestToMethodItems.add(item);
                }

                break;
            }
        }

        return new ModelAndView(BASE_PATH + "mapping").addObject("actionList", requestToMethodItems);
    }

    /**
     * 获取action映射列表
     * @param requestMappingHandlerConfig
     * @return
     */
    public static List<RequestToMethodItem> getActionList(RequestMappingHandlerConfig requestMappingHandlerConfig) {
        // 请求url和处理方法的映射
        List<RequestToMethodItem> requestToMethodItems = Lists.newArrayList();

        RequestMappingHandlerMapping requestMappingHandlerMapping = requestMappingHandlerConfig.requestMappingHandlerMapping();
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethodMap.entrySet()) {
            RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
            HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();  // controller的处理方法

            // 请求路径
            String requestUrl = requestMappingInfo.getPatternsCondition().toString()
                    .replace("[", "")
                    .replace("]", "");

            // 请求类型
            String requestType = requestMappingInfo.getMethodsCondition().toString()
                    .replace("[", "")
                    .replace("]", "");

            // 参数
            MethodParameter[] methodParameters = mappingInfoValue.getMethodParameters();

            String controllerName = mappingInfoValue.getBeanType().toString();  //

            String requestMethod = mappingInfoValue.getMethod().getName();

            // 返回header类型
            String responseType = requestMappingInfo.getProducesCondition().toString()
                    .replace("[", "")
                    .replace("]", "");

            List<RequestMethodParameter> parameters = Lists.newArrayListWithExpectedSize(methodParameters.length);
            for (MethodParameter methodParameter : methodParameters) {
                // 参数名称
                // 如果没有discover参数会是null.参考LocalVariableTableParameterNameDiscoverer
                methodParameter.initParameterNameDiscovery(discoverer);
                String parameterName = methodParameter.getParameterName();

                // 参数类型
                Class<?> parameterType= methodParameter.getParameterType();

                // 参数注解
                Object[] parameterAnnotations = methodParameter.getParameterAnnotations();

                // 注解
                String annotation = Arrays.toString(parameterAnnotations)
                        .replace("[", "")
                        .replace("]", "");

                RequestMethodParameter parameter = new RequestMethodParameter();
                parameter.setAnnotation(annotation);
                parameter.setName(parameterName);
                parameter.setType(parameterType.toString());
                parameters.add(parameter);
            }

            MvcDocument documentAnnotation = mappingInfoValue.getMethodAnnotation(MvcDocument.class);

            RequestToMethodItem item = new RequestToMethodItem();
            item.setRequestUrl(requestUrl);
            item.setRequestType(requestType);
            item.setParameters(parameters);
            item.setControllerName(controllerName);
            item.setDocument(documentAnnotation);
            item.setResponseType(responseType);
            item.setRequestMethod(requestMethod);

            requestToMethodItems.add(item);
        }

        Collections.sort(requestToMethodItems);

        return requestToMethodItems;
    }
}
