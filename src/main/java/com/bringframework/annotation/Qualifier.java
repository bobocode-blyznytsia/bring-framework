package com.bringframework.annotation;

import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation may be used on a field or parameter as a qualifier for candidate beans when autowiring.
 */
@Documented
@Target({ElementType.FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {

  String value() default "";
}
