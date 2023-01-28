package com.bringframework.registry;

import com.bringframework.context.ApplicationContext;
import java.util.Map;

/**
 * Interface for a registry that holds {@link BeanDefinition}.
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
   * Retrieve the {@link BeanDefinition} for the given bean name.
   *
   * @param name the name of the bean definition
   * @return the {@link BeanDefinition} for the given name or {@code null} if none is found
   */
  BeanDefinition getBeanDefinition(String name);

  /**
   * Retrieve all registered {@link BeanDefinition}s.
   *
   * @return a map of all registered bean definitions, with bean name as key and
   *     {@link BeanDefinition} as value
   */
  Map<String, BeanDefinition> getAllBeanDefinitions();
}
