package com.bringframework.exceptions;

/**
 * Exception thrown when multiple beans of the same type are found but expected one.
 */
public class NoUniqueBeanException extends BringException {

  public NoUniqueBeanException(Class<?> beanType) {
    super(String.format(""" 
        There are several beans with type %s, to get bean use method with bean name parameter:
           getBean(String name, Class<T> beanType)""", beanType.getSimpleName()));
  }
}
