package com.bringframework.factory.impl;

import com.bringframework.factory.BeanFactory;
import com.bringframework.factory.RawBeanProcessor;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is the main implementation of the {@link BeanFactory} interface.
 */
public class BeanFactoryImpl implements BeanFactory {
  private final Map<String, Object> rawBeansMap = new ConcurrentHashMap<>();
  private final BeanDefinitionRegistry beanDefinitionRegistry;
  //private List<BeanPostProcessor> beanPostProcessorList;

  @Getter
  @Setter
  private RawBeanProcessor rawBeanProcessor;

  public BeanFactoryImpl(BeanDefinitionRegistry beanDefinitionRegistry) {
    this.beanDefinitionRegistry = beanDefinitionRegistry;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> createBeans() {
    setRawBeanProcessor(new RawBeanProcessor(beanDefinitionRegistry, rawBeansMap));
    rawBeanProcessor.process();
    // TODO: 2/4/2023 create list of bean post processor and process raw beans
    //beanPostProcessorList.forEach(BeanPostProcessor::process);
    return rawBeansMap;
  }
}
