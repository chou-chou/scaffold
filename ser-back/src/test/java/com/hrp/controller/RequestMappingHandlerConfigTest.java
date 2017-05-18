package com.hrp.controller;

import com.hrp.controller.common.RequestMappingHandlerConfig;
import com.hrp.controller.common.SystemController;
import com.hrp.pojo.RequestToMethodItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RequestMappingHandlerConfigTest
 *
 * @author KVLT
 * @date 2017-05-16.
 */
public class RequestMappingHandlerConfigTest extends BaseMvcTest {

    @Autowired
    RequestMappingHandlerConfig requestMappingHandlerConfig;

    private static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    //Spring MVC 提取注解中URL映射
    @Test
    public void detectHandlerMethods(){
        final RequestMappingHandlerMapping requestMappingHandlerMapping = requestMappingHandlerConfig.requestMappingHandlerMapping ();
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        Set<RequestMappingInfo> mappings = map.keySet();
        Map<String, String> reversedMap = new HashMap<String, String>();
        for(RequestMappingInfo info : mappings) {
            HandlerMethod method = map.get(info);
            System.out.println (method.toString()+"===>"+info.getPatternsCondition().toString());
        }
    }

    @Test
    public void mapping() throws Exception {
        List<RequestToMethodItem> requestToMethodItems = SystemController.getActionList(requestMappingHandlerConfig);
        for (RequestToMethodItem requestToMethodItem : requestToMethodItems) {
            logger.info(requestToMethodItem.toString());
        }
    }
}
