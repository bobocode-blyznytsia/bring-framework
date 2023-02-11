package com.bringframework.factory;

import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.resolver.DefaultDependencyResolver;
import com.bringframework.resolver.DependencyResolver;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is the main implementation of the {@link BeanFactory} interface.
 */
public class BeanFactoryImpl implements BeanFactory {
  private final Map<String, Object> rawBeansMap = new ConcurrentHashMap<>();
  private final BeanDefinitionRegistry beanDefinitionRegistry;
  private final DependencyResolver dependencyResolver;
  private final List<BeanProcessor> beanProcessors = new ArrayList<>();
  private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

  public BeanFactoryImpl(BeanDefinitionRegistry beanDefinitionRegistry) {
    this.beanDefinitionRegistry = beanDefinitionRegistry;
    this.dependencyResolver = new DefaultDependencyResolver(this.beanDefinitionRegistry);
    this.beanProcessors
        .add(new ConfigBeanProcessor(this.beanDefinitionRegistry, rawBeansMap, dependencyResolver));
    this.beanProcessors
        .add(new RawBeanProcessor(rawBeansMap, this.beanDefinitionRegistry));
    this.beanPostProcessors
        .add(new AutowiredBeanPostProcessor(this.beanDefinitionRegistry, rawBeansMap,
            dependencyResolver));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> createBeans() {
    beanProcessors.forEach(BeanProcessor::process);
    beanPostProcessors.forEach(BeanPostProcessor::process);
    return rawBeansMap;
  }
}
