package com.hrp.annotation;

import java.lang.annotation.*;

/**
 * MvcDocument
 * 指明接口的参数，类，请求方式，作用和意义
 * @author KVLT
 * @date 2017-05-18.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MvcDocument {

    /**
     * 注释内容，推荐使用html标签进行编辑
     * @return
     */
    String info();

    /**
     * 返回给页面的参数，推荐使用html标签进行编辑
     * @return
     */
    String[] params() default "";

    /**
     * 接口提供者
     * @return
     */
    String author() default "";

    /**
     * 接口状态
     * @return
     */
    Status status() default Status.done;

    public static enum Status {
        fake,  // 假接口
        deleloping,  // 开发
        done,  // 开发完成
    }
}
