package com.bringframework.reader;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;

/**
 * Implementations of this interface are responsible for scanning package by given packageName
 * creating {@link BeanDefinition}s and registering them with a {@link BeanDefinitionRegistry}.
 */
public interface BeanDefinitionReader {

  /**
   * Registers beans from the specified package.
   *
   * @param packageName the package name where beans are located.
   */
  void registerBeans(String packageName);
}
