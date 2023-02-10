package com.bringframework.factory;

import java.util.Map;

/**
 * Interface for a factory that creates and manages beans.
 */
public interface BeanFactory {

  /**
   * Creates and initializes beans from a collection
   * of {@link com.bringframework.registry.BeanDefinition}s.
   *
   * @return a map of bean names and new instance of the bean
   */
  Map<String, Object> createBeans();
}
