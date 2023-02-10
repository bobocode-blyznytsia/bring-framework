package com.bringframework.exception;

/**
 * Exception thrown when no beans are found.
 */
public class NoSuchBeanException extends BringException {

  private static final String NO_SUCH_BEAN_EXCEPTION_DEFAULT_MESSAGE = "Bean with type %s does not exist!";

  private static final String NO_SUCH_BEAN_EXCEPTION_MESSAGE = "Bean with name %s, and type %s does not exist!";

  public NoSuchBeanException(Class<?> beanType) {
    super(NO_SUCH_BEAN_EXCEPTION_DEFAULT_MESSAGE.formatted(beanType.getSimpleName()));
  }

  public NoSuchBeanException(String beanName, Class<?> beanType) {
    super(NO_SUCH_BEAN_EXCEPTION_MESSAGE.formatted(beanName, beanType.getSimpleName()));
  }

}
