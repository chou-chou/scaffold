package com.hrp.utils.dataSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ConvertObject
 *
 * @author KVLT
 * @date 2017-03-14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConvertObject {

    public String name();
}
