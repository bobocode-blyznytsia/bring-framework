package com.bringframework.factory;

import com.bringframework.registry.BeanDefinition;
import java.util.Map;

/**
 * Interface for a factory that creates and manages beans.
 */
public interface BeanFactory {

  /**
   * Creates and initializes beans from a collection of {@link BeanDefinition}s.
   *
   * @return bean map, where key - bean name, and value - new instance of the bean
   */
  Map<String, Object> createBeans();
}
