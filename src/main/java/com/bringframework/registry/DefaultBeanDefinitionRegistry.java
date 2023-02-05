package com.bringframework.registry;

import com.bringframework.exceptions.BeanDefinitionDuplicateNameException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * Base implementation of {@link BeanDefinitionRegistry} used to store {@link BeanDefinition} and
 * retrieve them. This implementation is thread-safe.
 */
@Slf4j
public class DefaultBeanDefinitionRegistry implements BeanDefinitionRegistry {
  private final Map<String, BeanDefinition> registry = new ConcurrentHashMap<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
    if (registry.containsKey(name)) {
      throw new BeanDefinitionDuplicateNameException(String.format(
          "BeanDefinition with name %s already exists. Registry BeanDefinition class is %s and "
              + "provided for creation is %s", name, registry.get(name).getBeanClass(),
          beanDefinition.getBeanClass()));
    }
    registry.put(name, beanDefinition);
    log.debug("A new BeanDefinition with name {} for class {} has been registered successfully",
        name, beanDefinition.getBeanClass().getSimpleName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BeanDefinition getBeanDefinition(String name) {
    return registry.get(name);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Returns a defensive copy of all the bean definitions stored in the registry.
   */
  @Override
  public Map<String, BeanDefinition> getAllBeanDefinitions() {
    return Collections.unmodifiableMap(registry);
  }
}
