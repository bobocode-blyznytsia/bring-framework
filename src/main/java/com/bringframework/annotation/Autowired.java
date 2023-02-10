package com.bringframework.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks a field as to be autowired by Bring's dependency injection facilities.
 */
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface Autowired {
}
