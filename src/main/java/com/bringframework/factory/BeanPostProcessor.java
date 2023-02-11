package com.bringframework.factory;

import com.bringframework.registry.BeanDefinition;

/**
 * Implementations of this interface can be used to perform operations on all beans or
 * {@link BeanDefinition}s before and after initialization.
 */
public interface BeanPostProcessor {

  /**
   * Performs operations on the given bean instance before and after initialization.
   */
  void process();
}
