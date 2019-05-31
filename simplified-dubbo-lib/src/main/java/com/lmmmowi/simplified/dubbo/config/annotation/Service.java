package com.lmmmowi.simplified.dubbo.config.annotation;

import java.lang.annotation.*;

/**
 * @Author: mowi
 * @Date: 2019-05-30
 * @Description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface Service {

    Class<?> interfaceClass() default void.class;

    String interfaceName() default "";
}
