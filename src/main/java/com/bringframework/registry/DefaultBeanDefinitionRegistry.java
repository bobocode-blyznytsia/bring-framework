package com.bringframework.registry;

import com.bringframework.exception.BeanDefinitionDuplicateNameException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * Base implementation of {@link BeanDefinitionRegistry} used to store {@link BeanDefinition} and
 * {@link ConfigBeanDefinition} and retrieve them. This implementation is thread-safe.
 */
@Slf4j
public class DefaultBeanDefinitionRegistry implements BeanDefinitionRegistry {
  private final Map<String, BeanDefinition> registry = new ConcurrentHashMap<>();

  private final Map<String, ConfigBeanDefinition> configRegistry = new ConcurrentHashMap<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
    checkUniqueBeanName(name);
    registry.put(name, beanDefinition);
    log.debug("A new BeanDefinition with name {} for class {} has been registered successfully", name,
        beanDefinition.getBeanClass().getSimpleName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void registerConfigBeanDefinition(String name, ConfigBeanDefinition beanDefinition) {
    checkUniqueBeanName(name);
    configRegistry.put(name, beanDefinition);
    log.debug("A new BeanDefinition with name {} for class {} has been registered successfully", name,
        beanDefinition.factoryMethod().getReturnType().getSimpleName());
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
   */
  @Override
  public ConfigBeanDefinition getConfigBeanDefinition(String name) {
    return configRegistry.get(name);
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

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public boolean contains(String beanName) {
    return registry.containsKey(beanName);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Returns a defensive copy of all the config bean definitions stored in the registry.
   */
  @Override
  public Map<String, ConfigBeanDefinition> getAllConfigBeanDefinitions() {
    return Collections.unmodifiableMap(configRegistry);
  }

  private void checkUniqueBeanName(String name) {
    if (registry.containsKey(name) || configRegistry.containsKey(name)) {
      throw new BeanDefinitionDuplicateNameException(name);
    }
  }
}
