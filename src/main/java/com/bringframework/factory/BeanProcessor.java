package com.bringframework.factory;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;

/**
 * Interface is used as super type of bean processors.
 * See also: {@link RawBeanProcessor} and {@link ConfigBeanProcessor}
 *
 * @since 1.0
 */
public interface BeanProcessor {

  /**
   * Creates instances of beans by provided {@link BeanDefinition}s from {@link BeanDefinitionRegistry}.
   */
  void process();
}
