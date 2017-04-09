package com.wenyu.apt.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chan on 17/4/6.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Inherited
@Target(ElementType.TYPE)
public @interface MvpInject {
    Class<?> component();

    Class<?> module();

    Class<?> dependency() default Void.class;
}
