package com.bringframework.registry;

import java.lang.reflect.Field;
import java.util.Map;
import lombok.Builder;


/**
 * Represents the definition of a bean that is managed by the Bring IoC container.
 * It provides methods for getting information about the bean class,
 * as well as any autowired fields that the bean may have.
 */
@Builder
public class BeanDefinition {

  private Class<?> clazz;
  private Map<String, Field> autowiredFieldsMetadata;

  /**
   * Returns the class of the bean.
   *
   * @return the bean class
   */
  public Class<?> getBeanClass() {
    return this.clazz;
  }

  /**
   * Returns a map of autowired fields and their respective metadata for the bean. The keys in the map are field names
   * and the values are the corresponding class metadata.
   *
   * @return a map of autowired fields and their metadata
   */
  public Map<String, Field> getAutowiredFieldsMetadata() {
    return this.autowiredFieldsMetadata;
  }
}
