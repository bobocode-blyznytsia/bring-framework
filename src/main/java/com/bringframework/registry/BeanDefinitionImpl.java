package com.bringframework.registry;

import java.lang.reflect.Field;
import java.util.Map;
import lombok.Builder;

/**
 * Implementation of {@link BeanDefinition}
 * Represents the definition of a bean that is managed by the Bring IoC container.
 */
@Builder
public class BeanDefinitionImpl implements BeanDefinition {

  private Class<?> clazz;
  private Map<String, Field> autowiredFieldsMetadata;

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getBeanClass() {
    return this.clazz;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Field> getAutowiredFieldsMetadata() {
    return this.autowiredFieldsMetadata;
  }
}
