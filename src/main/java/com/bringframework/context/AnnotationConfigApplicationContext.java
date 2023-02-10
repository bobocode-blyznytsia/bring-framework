package com.bringframework.context;

import static com.bringframework.util.BeanUtils.validatePackageName;

import com.bringframework.exception.NoSuchBeanException;
import com.bringframework.exception.NoUniqueBeanException;
import com.bringframework.factory.BeanFactory;
import com.bringframework.factory.impl.BeanFactoryImpl;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.DefaultBeanDefinitionRegistry;
import com.bringframework.scanner.DefaultBeanDefinitionScanner;
import com.bringframework.scanner.DefaultConfigBeanDefinitionScanner;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link ApplicationContext} that uses annotation configuration to search and create beans.
 */
@Slf4j
public class AnnotationConfigApplicationContext implements ApplicationContext {

  private final Map<String, Object> beans;

  public AnnotationConfigApplicationContext(String packageName) {
    validatePackageName(packageName);
    log.trace("Application context is collecting...");
    BeanDefinitionRegistry beanDefinitionRegistry = new DefaultBeanDefinitionRegistry();
    new DefaultBeanDefinitionScanner(beanDefinitionRegistry).registerBeans(packageName);
    new DefaultConfigBeanDefinitionScanner(beanDefinitionRegistry).registerConfigBeans(packageName);
    BeanFactory beanFactory = new BeanFactoryImpl(beanDefinitionRegistry);
    this.beans = beanFactory.createBeans();
  }

  @Override
  public <T> T getBean(Class<T> beanType) {
    Map<String, T> beansOfSpecifiedType = getAllBeans(beanType);
    if (beansOfSpecifiedType.size() > 1) {
      throw new NoUniqueBeanException(beanType);
    }
    T foundBean =
        beansOfSpecifiedType.values().stream().findFirst().orElseThrow(() -> new NoSuchBeanException(beanType));
    log.trace("Retrieved bean with type {}", beanType.getSimpleName());
    return foundBean;
  }

  @Override
  public <T> T getBean(String name, Class<T> beanType) {
    Map<String, T> beansOfSpecifiedType = getAllBeans(beanType);
    T foundBean = beansOfSpecifiedType.get(name);
    if (foundBean == null) {
      throw new NoSuchBeanException(name, beanType);
    }
    log.trace("Retrieved bean with name {} and type {}", name, beanType.getSimpleName());
    return foundBean;
  }

  @Override
  public <T> Map<String, T> getAllBeans(Class<T> beanType) {
    log.trace("Retrieved all beans with type {}", beanType.getSimpleName());
    return beans.entrySet().stream().filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
  }

  //TODO Remove. Added for demonstration purpose
  @Override
  public Map<String, Object> getAllBeans() {
    return beans;
  }

}
