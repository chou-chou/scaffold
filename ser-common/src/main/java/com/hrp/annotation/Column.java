package com.hrp.annotation;

import java.lang.annotation.*;

/**
 * Column
 *
 * @author KVLT
 * @date 2017-03-23.
 */
@Retention(RetentionPolicy.RUNTIME)
// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ ElementType.FIELD, ElementType.METHOD })
// 定义注解的作用目标**作用范围字段、枚举的常量/方法
@Documented
// 说明该注解将被包含在javadoc中
public @interface Column {

    String name = "columnName";  // 列名

    /**
     * (2)    是否在该列上设置唯一约束
     * @return
     */
    boolean unique() default false;

    /**
     *  (3)   列可空？
     * @return
     */
    boolean nullable() default true;

    /**
     * (4) 该列是否作为生成 insert语句的一个列
     * @return
     */
    boolean insertable() default true;

    /**
     * (5)  该列是否作为生成 update语句的一个列
     * @return
     */
    boolean updatable() default true;

    /**
     * (6)  默认值
     * @return
     */
    String columnDefinition() default"";

    /**
     * (7)             定义对应的表（deault 是主表）
     * @return
     */
    String table() default "";

    /**
     * (8)              列长度
     * @return
     */
    int length() default 255;

    /**
     * // decimalprecision (9)  decimal精度
     * @return
     */
    int precision() default 0;

    /**
     * // decimal scale        (10) decimal长度
     * @return
     */
    int scale() default 0;
}
