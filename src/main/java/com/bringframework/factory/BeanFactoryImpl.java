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
  private final List<BeanProcessor> beanProcessors = new ArrayList<>();
  private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

  public BeanFactoryImpl(BeanDefinitionRegistry beanDefinitionRegistry) {
    DependencyResolver dependencyResolver =
        new DefaultDependencyResolver(beanDefinitionRegistry);
    this.beanProcessors
        .add(new ConfigBeanProcessor(beanDefinitionRegistry, rawBeansMap, dependencyResolver));
    this.beanProcessors
        .add(new RawBeanProcessor(rawBeansMap, beanDefinitionRegistry));
    this.beanPostProcessors
        .add(new AutowiredBeanPostProcessor(beanDefinitionRegistry, rawBeansMap,
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
