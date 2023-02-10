package com.bringframework.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

//TODO Add documentation
@Documented
@Target({TYPE})
@Retention(RUNTIME)
public @interface Configuration {
}
