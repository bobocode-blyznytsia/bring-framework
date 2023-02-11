package com.bringframework.exception;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;

/**
 * Exception thrown when {@link BeanDefinition} with the same name already exists in the
 * {@link BeanDefinitionRegistry}.
 **/
public class BeanDefinitionDuplicateNameException extends BringException {

  private static final String DEFAULT_BEAN_DEFINITION_DUPLICATE_NAME_EXCEPTION_MESSAGE =
      "BeanDefinition with name %s already exists";

  public BeanDefinitionDuplicateNameException(String beanName) {
    super(DEFAULT_BEAN_DEFINITION_DUPLICATE_NAME_EXCEPTION_MESSAGE.formatted(beanName));
  }
}
