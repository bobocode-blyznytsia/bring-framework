package com.bringframework.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.bringframework.context.AnnotationConfigApplicationContext;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that a class declares one or more {@link Bean} methods and may be processed by the Bring
 * container to generate bean definitions and service requests for those beans at runtime
 *
 * <p>See the {@link AnnotationConfigApplicationContext} javadocs for further details
 */
@Documented
@Target({TYPE})
@Retention(RUNTIME)
public @interface Configuration {
}
