package com.bringframework.exception;

/**
 * Exception thrown when multiple beans of the same type are found but expected one.
 */
public class NoUniqueBeanException extends BringException {

  private static final String NO_UNIQUE_BEAN_EXCEPTION_DEFAULT_MESSAGE = """ 
      There are several beans with type %s, to get bean use method with bean name parameter:
         getBean(String name, Class<T> beanType)""";

  public NoUniqueBeanException(Class<?> beanType) {
    super(NO_UNIQUE_BEAN_EXCEPTION_DEFAULT_MESSAGE.formatted(beanType.getSimpleName()));
  }
}
