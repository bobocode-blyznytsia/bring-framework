package com.bringframework.registry;

import com.bringframework.exceptions.BeanDefinitionDuplicateNameException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * Base implementation of {@link BeanDefinitionRegistry} used to store {@link BeanDefinition} and retrieve them. This
 * implementation is thread-safe.
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
    checkUniqueBeanName(name, beanDefinition.getBeanClass().getSimpleName());
    registry.put(name, beanDefinition);
    log.debug("A new BeanDefinition with name {} for class {} has been registered successfully", name,
        beanDefinition.getBeanClass().getSimpleName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void registerConfigBeanDefinition(String name, ConfigBeanDefinition beanDefinition) {
    checkUniqueBeanName(name, beanDefinition.factoryMethod().getReturnType().getSimpleName());
    configRegistry.put(name, beanDefinition);
    log.debug("A new BeanDefinition with name {} for class {} has been registered successfully", name,
        beanDefinition.factoryMethod().getReturnType().getSimpleName());
  }

  private void checkUniqueBeanName(String name, String className) {
    if (registry.containsKey(name) || configRegistry.containsKey(name)) {
      throw new BeanDefinitionDuplicateNameException(String.format(
          "BeanDefinition with name %s already exists. Registry BeanDefinition class is %s and " +
              "provided for creation is %s", name, registry.get(name).getBeanClass(), className));
    }
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
   * <p>Returns a defensive copy of all the config bean definitions stored in the registry.
   */
  @Override
  public Map<String, ConfigBeanDefinition> getAllConfigBeanDefinitions() {
    return Collections.unmodifiableMap(configRegistry);
  }
}
