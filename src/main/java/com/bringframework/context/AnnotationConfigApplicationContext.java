package com.bringframework.context;

import com.bringframework.exceptions.NoSuchBeanException;
import com.bringframework.exceptions.NoUniqueBeanException;
import com.bringframework.factory.BeanFactory;
import com.bringframework.factory.impl.BeanFactoryImpl;
import com.bringframework.reader.BeanDefinitionReader;
import com.bringframework.reader.ConfigBeanDefinitionReader;
import com.bringframework.reader.DefaultBeanDefinitionReader;
import com.bringframework.reader.DefaultConfigBeanDefinitionReader;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.DefaultBeanDefinitionRegistry;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@code ApplicationContext} that uses annotation configuration to search and
 * create beans.
 */
@Slf4j
public class AnnotationConfigApplicationContext implements ApplicationContext {

  private Map<String, Object> beans;

  public AnnotationConfigApplicationContext() {
    log.trace("Application context is collecting...");
    BeanDefinitionRegistry beanDefinitionRegistry = new DefaultBeanDefinitionRegistry();
    BeanDefinitionReader beanDefinitionReader = new DefaultBeanDefinitionReader(
        beanDefinitionRegistry);
    ConfigBeanDefinitionReader configBeanDefinitionReader = new DefaultConfigBeanDefinitionReader(
      beanDefinitionRegistry);
    //TODO package name provided for demonstration purpose
    beanDefinitionReader.registerBeans("com.bringframework");
    configBeanDefinitionReader.registerConfigBeans("com.bringframework");
    BeanFactory beanFactory = new BeanFactoryImpl(beanDefinitionRegistry);
    beans = beanFactory.createBeans();
  }

  @Override
  public <T> T getBean(Class<T> beanType) {
    Map<String, T> beansOfSpecifiedType = getAllBeans(beanType);
    if (beansOfSpecifiedType.size() > 1) {
      throw new NoUniqueBeanException(beanType);
    }
    T foundBean = beansOfSpecifiedType.values().stream()
        .findFirst()
        .orElseThrow(() -> new NoSuchBeanException(String.format(
            "Bean with type %s does not exist!",
            beanType.getSimpleName())));
    log.trace("Retrieved bean with type {}", beanType.getSimpleName());
    return foundBean;
  }

  @Override
  public <T> T getBean(String name, Class<T> beanType) {
    Map<String, T> beansOfSpecifiedType = getAllBeans(beanType);
    T foundBean = beansOfSpecifiedType.get(name);
    if (foundBean == null) {
      throw new NoSuchBeanException(String.format(
          "Bean with name %s, and type %s does not exist!",
          name,
          beanType.getSimpleName())
      );
    }
    log.trace("Retrieved bean with name {} and type {}", name, beanType.getSimpleName());
    return foundBean;
  }

  @Override
  public <T> Map<String, T> getAllBeans(Class<T> beanType) {
    log.trace("Retrieved all beans with type {}", beanType.getSimpleName());
    return beans.entrySet().stream()
        .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
  }

  //TODO Remove. Added for demonstration purpose
  @Override
  public Map<String, Object> getAllBeans() {
    return beans;
  }

}
