package com.bringframework.registry;

import com.bringframework.context.ApplicationContext;
import java.util.Map;

/**
 * Interface for a registry that holds {@link BeanDefinition} and {@link ConfigBeanDefinition}.
 *
 * <p>This is used by the {@link ApplicationContext} to create and manage bean instances.
 **/
public interface BeanDefinitionRegistry {

  /**
   * Register a {@link BeanDefinition} with the given name.
   *
   * @param name           the name of the bean definition
   * @param beanDefinition the {@link BeanDefinition} instance to register
   */
  void registerBeanDefinition(String name, BeanDefinition beanDefinition);

  /**
   * Register a {@link ConfigBeanDefinition} with the given name.
   *
   * @param name                 the name of the bean definition
   * @param configBeanDefinition the {@link ConfigBeanDefinition} instance to register
   */
  void registerConfigBeanDefinition(String name, ConfigBeanDefinition configBeanDefinition);

  /**
   * Retrieve the {@link BeanDefinition} for the given bean name.
   *
   * @param name the name of the bean definition
   * @return the {@link BeanDefinition} for the given name.
   */
  BeanDefinition getBeanDefinition(String name);

  /**
   * Retrieve the {@link ConfigBeanDefinition} for the given bean name.
   *
   * @param name the name of the config bean definition
   * @return the {@link ConfigBeanDefinition} for the given name.
   */
  ConfigBeanDefinition getConfigBeanDefinition(String name);

  /**
   * Retrieve all registered {@link BeanDefinition}s.
   *
   * @return a map of all registered bean definitions, with bean name as key and {@link BeanDefinition} as value
   */
  Map<String, BeanDefinition> getAllBeanDefinitions();

  /**
   * Checks if bean registry contains beanDefinition for provided bean name
   *
   * @param beanName bean name
   * @return true if contains and false otherwise
   */
  boolean contains(String beanName);

  /**
   * Retrieve all registered {@link ConfigBeanDefinition}s.
   *
   * @return a map of all registered config bean definitions, with bean name as key and {@link ConfigBeanDefinition} as
   *     value
   */
  Map<String, ConfigBeanDefinition> getAllConfigBeanDefinitions();
}
