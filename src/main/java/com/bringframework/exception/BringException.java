package com.bringframework.exception;

/**
 * Exception thrown by Bring application. It is a super class of all exceptions that can produce Bring application.
 */
public class BringException extends RuntimeException {

  public BringException(String message) {
    super(message);
  }

  public BringException(String message, Throwable cause) {
    super(message, cause);
  }

}
