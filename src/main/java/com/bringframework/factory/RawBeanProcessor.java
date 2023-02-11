package com.bringframework.factory;

import static com.bringframework.util.BeanUtils.createInstance;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.resolver.DependencyResolver;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link BeanProcessor} that is responsible for processing {@link BeanDefinition}s
 * of rowBeans provided  from {@link BeanDefinitionRegistry}.
 *
 * <p>RawBeanProcessor is not responsible for injecting other beans, but {@link AutowiredBeanPostProcessor}.
 *
 * @since 1.0
 */
@Slf4j
public class RawBeanProcessor implements BeanProcessor {

  private final Map<String, Object> rawBeanMap;
  private final BeanDefinitionRegistry beanDefinitionRegistry;
  private final DependencyResolver dependencyResolver;

  public RawBeanProcessor(BeanDefinitionRegistry beanDefinitionRegistry,
                          Map<String, Object> rawBeanMap,
                          DependencyResolver dependencyResolver) {
    this.beanDefinitionRegistry = beanDefinitionRegistry;
    this.rawBeanMap = rawBeanMap;
    this.dependencyResolver = dependencyResolver;
  }

  /**
   * {@inheritDoc}
   */
  public void process() {
    Map<String, BeanDefinition> beanDefinitions =
        this.beanDefinitionRegistry.getAllBeanDefinitions();
    var beansSize = beanDefinitions.size();
    log.debug("Creating {} beans without dependencies found in bean definition registry", beansSize);
    beanDefinitions.forEach(this::initializeBean);
    log.debug("Finished creating {} beans", beansSize);
  }

  private void initializeBean(String beanName, BeanDefinition beanDefinition) {
    var beanClass = beanDefinition.getBeanClass();
    Object bean = createInstance(beanClass);
    rawBeanMap.put(beanName, bean);
    log.debug("Bean with name {} of type {} has been created", beanName, beanClass.getSimpleName());
  }

}
