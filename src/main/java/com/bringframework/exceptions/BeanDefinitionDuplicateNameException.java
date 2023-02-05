package com.bringframework.exceptions;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;

/**
 * Exception thrown when {@link BeanDefinition} with the same name already exists in the
 * {@link BeanDefinitionRegistry}.
 **/
public class BeanDefinitionDuplicateNameException extends RuntimeException {

  public BeanDefinitionDuplicateNameException(String message) {
    super(message);
  }
}
