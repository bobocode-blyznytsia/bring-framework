package com.bringframework.registry;

import java.lang.reflect.Field;
import java.util.Map;
import lombok.Builder;

/**
 * Implementation of {@link BeanDefinition}
 * Represents the definition of a bean that is managed by the Bring IoC container.
 */
//TODO fix javadocs as it hasn't implemented interface yet
@Builder
public class BeanDefinition {

  private Class<?> clazz;
  private Map<String, Field> autowiredFieldsMetadata;

  /**
   * {@inheritDoc}
   */
  public Class<?> getBeanClass() {
    return this.clazz;
  }

  /**
   * Returns a map of autowired fields and their respective metadata for the bean. The keys in
   * the map are field names and the values are the corresponding class metadata.
   *
   * @return a map of autowired fields and their metadata
   */
  public Map<String, Field> getAutowiredFieldsMetadata() {
    return this.autowiredFieldsMetadata;
  }
}
