package com.bringframework.exception;

import java.lang.reflect.Field;

/**
 * Exception thrown when {@link com.bringframework.factory.AutowiredBeanPostProcessor} fails to inject a dependency
 */
public class BeanInjectionException extends BringException {

  private static final String BEAN_INJECTION_EXCEPTION_DEFAULT_MESSAGE = "Failed to inject bean into field '%s'";

  public BeanInjectionException(Field field, Throwable cause) {
    super(BEAN_INJECTION_EXCEPTION_DEFAULT_MESSAGE.formatted(field.getName()), cause);
  }
}
