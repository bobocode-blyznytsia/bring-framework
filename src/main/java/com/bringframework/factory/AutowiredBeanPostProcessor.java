package com.bringframework.factory;

import com.bringframework.exceptions.BeanInjectionException;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.resolver.DefaultDependencyResolver;
import com.bringframework.resolver.DependencyResolver;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * BeanPostProcessor that receives a map of raw bean instances, reads list of fields marked as
 * autowired from {@link com.bringframework.registry.BeanDefinition} and injects into ther
 * corresponding dependencies resolved by {@link DependencyResolver}
 */
public class AutowiredBeanPostProcessor implements BeanPostProcessor {
  private final BeanDefinitionRegistry definitionRegistry;
  private final Map<String, Object> rawBeans;
  private final DependencyResolver dependencyResolver;

  public AutowiredBeanPostProcessor(BeanDefinitionRegistry registry, Map<String, Object> rawBeans) {
    this.rawBeans = rawBeans;
    definitionRegistry = registry;
    dependencyResolver = new DefaultDependencyResolver(definitionRegistry.getAllBeanDefinitions());
  }


  @Override
  public void process() {
    rawBeans.forEach(this::processBean);
  }

  private void processBean(String beanName, Object bean) {
    var beanDefinition = definitionRegistry.getBeanDefinition(beanName);

    beanDefinition.getAutowiredFieldsClassMetadata().keySet().stream()
        .map(fieldName -> getFieldByName(beanDefinition.getBeanClass(), fieldName))
        .forEach(field -> populateField(field, bean));
  }

  private Field getFieldByName(Class<?> clazz, String fieldName) {
    try {
      return clazz.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      throw new BeanInjectionException(
          "Cannot find field '%s' in %s class".formatted(fieldName, clazz), e);
    }
  }

  private void populateField(Field field, Object targetBean) {
    var candidate = getCandidateOfType(field.getType());
    try {
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
