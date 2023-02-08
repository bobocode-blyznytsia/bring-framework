package com.bringframework.factory.impl;

import com.bringframework.factory.AutowiredBeanPostProcessor;
import com.bringframework.factory.BeanFactory;
import com.bringframework.factory.BeanPostProcessor;
import com.bringframework.factory.RawBeanProcessor;
import com.bringframework.registry.BeanDefinitionRegistry;
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
  private final RawBeanProcessor rawBeanProcessor;
  private final List<BeanPostProcessor> beanPostProcessorList;

  public BeanFactoryImpl(BeanDefinitionRegistry beanDefinitionRegistry) {
    this.beanDefinitionRegistry = beanDefinitionRegistry;
    this.rawBeanProcessor = new RawBeanProcessor(this.beanDefinitionRegistry, rawBeansMap);
    this.beanPostProcessorList = new ArrayList<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> createBeans() {
    rawBeanProcessor.process();
    beanPostProcessorList.add(new AutowiredBeanPostProcessor(beanDefinitionRegistry, rawBeansMap));
    beanPostProcessorList.forEach(BeanPostProcessor::process);
    return rawBeansMap;
  }
}
