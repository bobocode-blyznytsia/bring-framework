package com.bringframework.annotation;


import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to Indicate that a method produces a bean to be managed by the Bring container
 */
@Documented
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
}
