package com.bringframework.reader;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;

/**
 * Implementations of this interface are responsible for scanning package by given classpath for configuration beans
 * creating {@link BeanDefinition}s and registering them with a {@link BeanDefinitionRegistry}.
 */
public interface ConfigBeanDefinitionReader {

  /**
   * Register configuration beans from a given class path.
   *
   * @param classPath the class path to search for configuration beans
   */
  void registerConfigBeans(String classPath);
}
