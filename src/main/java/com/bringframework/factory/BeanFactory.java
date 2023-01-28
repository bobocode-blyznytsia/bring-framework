package com.bringframework.factory;

import com.bringframework.registry.BeanDefinition;
import java.util.Collection;

/**
 * Interface for a factory that should create and manage beans.
 */
public interface BeanFactory {

  /**
   * Creates and initializes beans from a collection of {@link BeanDefinition}s.
   *
   * @param beanDefinitions the collection of {@link BeanDefinition}s to create and initialize beans
   *                        from
   */
  void createBeans(Collection<BeanDefinition> beanDefinitions);
}
