package com.bringframework.context;

import static com.bringframework.util.BeanUtils.validatePackageName;

import com.bringframework.exceptions.NoSuchBeanException;
import com.bringframework.exceptions.NoUniqueBeanException;
import com.bringframework.factory.BeanFactory;
import com.bringframework.factory.impl.BeanFactoryImpl;
import com.bringframework.reader.DefaultBeanDefinitionReader;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.DefaultBeanDefinitionRegistry;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link ApplicationContext} that uses annotation configuration to search and
 * create beans.
 */
@Slf4j
public class AnnotationConfigApplicationContext implements ApplicationContext {

  private final Map<String, Object> beans;

  public AnnotationConfigApplicationContext(String packageName) {
    validatePackageName(packageName);
    log.trace("Application context is collecting...");
    BeanDefinitionRegistry beanDefinitionRegistry = new DefaultBeanDefinitionRegistry();
    new DefaultBeanDefinitionReader(beanDefinitionRegistry).registerBeans(packageName);
    //ConfigBeanDefinitionReader configBeanDefinitionReader = new ConfigBeanDefinitionReaderImpl(
    //  beanDefinitionRegistry);
    BeanFactory beanFactory = new BeanFactoryImpl(beanDefinitionRegistry);
    this.beans = beanFactory.createBeans();
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

}
