package com.hrp.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 * Swagger-UI的配置
 *
 * @author KVLT
 * @date 2017-06-02.
 */
//@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/r/.*"))  // 过滤的接口
                .build()
                .apiInfo(serApiInfo());
    }

    @Bean
    public Docket serApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ser")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/r/.*"))  // 过滤的接口
                .build()
                .apiInfo(serApiInfo());
    }

    private ApiInfo serApiInfo() {
        Contact contact = new Contact("hrp", "", "");
        return new ApiInfoBuilder()
                .title("API接口列表")
                .description("Restful风格API")
                .version("0.1")
                .contact(contact)
                .build();
    }
}
