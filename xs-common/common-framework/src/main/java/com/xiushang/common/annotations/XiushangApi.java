package com.xiushang.common.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XiushangApi {

    /**
     * Hides the operations under this resource.
     *
     * @return true if the api should be hidden from the swagger documentation
     */
    boolean hidden() default false;
}
