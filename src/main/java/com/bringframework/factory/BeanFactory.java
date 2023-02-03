package com.bringframework.factory;

import com.bringframework.registry.BeanDefinition;
import java.util.Collection;
import java.util.Map;

/**
 * Interface for a factory that creates and manages beans.
 */
public interface BeanFactory {

  /**
   * Creates and initializes beans from a collection of {@link BeanDefinition}s.
   *
   * @param beanDefinitions the collection of {@link BeanDefinition}s to create and initialize beans
   *                        from
   */
  void createBeans(Collection<BeanDefinition> beanDefinitions);

  /**
   * Returns created and ready for usage beans.
   */
  Map<String, Object> getReadyBeans();

}
