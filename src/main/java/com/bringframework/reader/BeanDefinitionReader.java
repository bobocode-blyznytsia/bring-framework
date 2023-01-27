package com.bringframework.reader;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;

/**
 * Implementations of this interface are responsible for scanning package by given classpath creating
 * {@link BeanDefinition}s and registering them with a {@link BeanDefinitionRegistry}.
 */
public interface BeanDefinitionReader {

  /**
   * Registers beans from the specified class path.
   *
   * @param classPath the class path where beans are located.
   */
  void registerBeans(String classPath);
}
