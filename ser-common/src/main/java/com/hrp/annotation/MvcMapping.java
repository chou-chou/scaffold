package com.hrp.annotation;

import java.lang.annotation.*;

/**
 * MvcMapping
 * 自定义注解 （配置url和路径的对应关系） 用于url和资源的映射
 * @author KVLT
 * @date 2017-05-19.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MvcMapping {

    /**
     * 注释内容
     * @return
     */
    String info() default "";

    /**
     * action中的url（对应GET方法）
     * @return
     */
    String tag();

    /**
     * action中url对应的视图路径（对应到具体的物理路径）
     * @return
     */
    String path();

    /**
     * 视图映射的类型
     * @return
     */
    ViewType type() default ViewType.JSP;

    /**
     * 视图映射类型（定义访问路径前缀）
     */
    public static enum ViewType {
        JSP("/WEB-INF/views/", ".jsp"),        // 对应jsp
        HTML("", ".html"),
        FTL("/WEB-INF/ftls/", ".html")         // 对应freemarker
        ;

        private final String prefix;  // 前缀
        private final String suffix;  // 后缀

        ViewType(String prefix, String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }
    }
}
