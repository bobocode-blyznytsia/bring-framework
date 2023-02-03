package com.bringframework.factory;

import static com.bringframework.util.BeanUtils.createInstance;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Creates instances of beans by provided {@link BeanDefinition}s from
 * {@link BeanDefinitionRegistry}.
 * RawBeanProcessor is not responsible for injecting other beans,
 * but {@link AutowiredBeanPostProcessor}.
 */
@Slf4j
public class RawBeanProcessor {

  private final Map<String, Object> rawBeanMap;

  private final BeanDefinitionRegistry beanDefinitionRegistry;

  public RawBeanProcessor(BeanDefinitionRegistry beanDefinitionRegistry,
                          Map<String, Object> rawBeanMap) {
    this.beanDefinitionRegistry = beanDefinitionRegistry;
    this.rawBeanMap = rawBeanMap;
  }

  public void process() {
    Map<String, BeanDefinition> beanDefinitions =
        this.beanDefinitionRegistry.getAllBeanDefinitions();
    log.debug("Started creating {} beans without dependencies found in bean definition registry.",
        beanDefinitions.size());
    beanDefinitions.forEach(this::initializeBean);
    log.debug("Finished creating beans.");
  }

  private void initializeBean(String beanName, BeanDefinition beanDefinition) {
    Class<Object> beanClass = beanDefinition.getBeanClass();
    Object bean = createInstance(beanClass);
    rawBeanMap.put(beanName, bean);
    log.debug("Bean with name {} of type {} has been created.", beanName,
        beanClass.getSimpleName());
  }

}
