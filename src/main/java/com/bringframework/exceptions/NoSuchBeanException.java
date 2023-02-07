package com.bringframework.exceptions;

/**
 * Exception thrown when no beans are found.
 */
public class NoSuchBeanException extends BringException {

  public NoSuchBeanException(String message) {
    super(message);
  }

}
