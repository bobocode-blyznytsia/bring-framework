package com.bringframework.factory;

import static com.bringframework.util.BeanUtils.createInstance;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.util.Map;

/**
 * Creates instances of beans by provided {@link BeanDefinition}s from
 * {@link BeanDefinitionRegistry}.
 * RawBeanProcessor is not responsible for injecting other beans,
 * but {@link AutowiredBeanPostProcessor}.
 */
public class RawBeanProcessor {

  private final Map<String, Object> rawBeanMap;

  private final BeanDefinitionRegistry beanDefinitionRegistry;

  public RawBeanProcessor(BeanDefinitionRegistry beanDefinitionRegistry,
                          Map<String, Object> rawBeanMap) {
    this.beanDefinitionRegistry = beanDefinitionRegistry;
    this.rawBeanMap = rawBeanMap;
  }

  public void process() {
    this.beanDefinitionRegistry.getAllBeanDefinitions()
        .forEach((beanName, beanDefinition) -> rawBeanMap.put(beanName,
            createInstance(beanDefinition.getBeanClass())));
  }

}
