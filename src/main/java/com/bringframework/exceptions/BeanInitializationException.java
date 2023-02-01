package com.bringframework.exceptions;

/**
 * Exception thrown when bean instance couldn't be created.
 */
public class BeanInitializationException extends RuntimeException {
  public BeanInitializationException(Class<?> beanType, Throwable cause) {
    super(String.format("Couldn't initialize bean with type=%s. Reason: %s", beanType,
        cause.getMessage()), cause);
  }
}
