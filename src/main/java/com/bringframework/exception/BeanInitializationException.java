package com.bringframework.exception;

/**
 * Exception thrown when bean instance couldn't be created.
 */
public class BeanInitializationException extends BringException {

  private static final String BEAN_INITIALIZATION_EXCEPTION_DEFAULT_MESSAGE =
      "Couldn't initialize bean with type=%s. Reason: %s";

  public BeanInitializationException(Class<?> beanType, Throwable cause) {
    super(BEAN_INITIALIZATION_EXCEPTION_DEFAULT_MESSAGE.formatted(beanType, cause.getMessage()), cause);
  }
}
