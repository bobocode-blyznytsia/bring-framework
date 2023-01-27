package com.bringframework.registry;

import java.util.Map;

/**
 * Represents the definition of a bean that is managed by the Bring IoC container. It provides methods for getting
 * information about the bean class, as well as any autowired fields that the bean may have.
 */
public interface BeanDefinition {

  /**
   * Returns the class of the bean.
   *
   * @return the bean class
   */
  <T> Class<T> getBeanClass();

  /**
   * Returns a map of autowired fields and their respective class metadata for the bean. The keys in the map are field
   * names and the values are the corresponding class metadata.
   *
   * @return a map of autowired fields and their class metadata
   */
  <T> Map<String, Class<T>> getAutowiredFieldsClassMetadata();
}
