package com.bringframework.factory;

import com.bringframework.exceptions.BeanInjectionException;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.resolver.DefaultDependencyResolver;
import com.bringframework.resolver.DependencyResolver;
import java.lang.reflect.Field;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * BeanPostProcessor that receives a map of raw bean instances, reads list of fields marked as
 * autowired from {@link com.bringframework.registry.BeanDefinition} and injects into ther
 * corresponding dependencies resolved by {@link DependencyResolver}
 */

@Slf4j
public class AutowiredBeanPostProcessor implements BeanPostProcessor {
  private final BeanDefinitionRegistry definitionRegistry;
  private final Map<String, Object> rawBeans;
  private final DependencyResolver dependencyResolver;

  public AutowiredBeanPostProcessor(BeanDefinitionRegistry registry, Map<String, Object> rawBeans) {
    this.rawBeans = rawBeans;
    this.definitionRegistry = registry;
    this.dependencyResolver = new DefaultDependencyResolver(definitionRegistry.getAllBeanDefinitions());
  }


  @Override
  public void process() {
    log.debug("Injecting dependencies into autowired fields");
    rawBeans.forEach(this::processBean);
    log.debug("Injecting dependencies for autowired fields complete");
  }

  private void processBean(String beanName, Object bean) {
    var beanDefinition = definitionRegistry.getBeanDefinition(beanName);
    log.debug("Injecting autowired field dependencies for bean {}", beanName);
    beanDefinition.getAutowiredFieldsMetadata().values()
        .forEach(field -> populateField(field, bean));
  }


  private void populateField(Field field, Object targetBean) {
    var candidate = getCandidateOfType(field.getType());
    try {
      log.debug("Injecting bean of class {} into field {}", targetBean, field);
      field.setAccessible(true);
      field.set(targetBean, candidate);
      field.setAccessible(false);
    } catch (IllegalAccessException e) {
      throw new BeanInjectionException(
          "Failed to inject bean into field '%s'".formatted(field.getName()), e);
    }
  }

  private Object getCandidateOfType(Class<?> candidateClass) {
    return rawBeans.get(dependencyResolver.getCandidateNameOfType(candidateClass));
  }

}
