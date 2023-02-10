package com.bringframework.annotations;


import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//TODO Add documentation
@Documented
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
}
