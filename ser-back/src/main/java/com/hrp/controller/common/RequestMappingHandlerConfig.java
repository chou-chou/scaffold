package com.hrp.controller.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * RequestMappingHandlerConfig
 *
 * @author KVLT
 * @date 2017-05-16.
 */
@Configuration
public class RequestMappingHandlerConfig {
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
        return mapping;
    }
}
