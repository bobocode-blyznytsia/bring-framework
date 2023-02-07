package com.bringframework.exceptions;

/**
 * Exception thrown when {@link com.bringframework.factory.AutowiredBeanPostProcessor} fails to
 * inject a dependency
 */
public class BeanInjectionException extends BringException {

  public BeanInjectionException(String message, Throwable cause) {
    super(message, cause);
  }
}
