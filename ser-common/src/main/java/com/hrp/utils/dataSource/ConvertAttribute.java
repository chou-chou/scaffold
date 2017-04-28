package com.hrp.utils.dataSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ConvertAttribute
 *
 * @author KVLT
 * @date 2017-03-14.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConvertAttribute {

    public String name();

    public String type() default "field";

    public String target() default "";
}
