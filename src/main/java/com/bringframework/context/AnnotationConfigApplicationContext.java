package com.bringframework.context;

import com.bringframework.exceptions.NoSuchBeanException;
import com.bringframework.exceptions.NoUniqueBeanException;
import com.bringframework.factory.BeanFactory;
import com.bringframework.reader.BeanDefinitionReader;
import com.bringframework.reader.ConfigBeanDefinitionReader;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@code ApplicationContext} that uses annotation configuration to search and
 * create beans.
 */
public class AnnotationConfigApplicationContext implements ApplicationContext {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      AnnotationConfigApplicationContext.class);
  private Map<String, Object> beans;
  private BeanDefinitionRegistry beanDefinitionRegistry;
  private BeanDefinitionReader beanDefinitionReader;
  private ConfigBeanDefinitionReader configBeanDefinitionReader;
  private BeanFactory beanFactory;


  public AnnotationConfigApplicationContext() {
    LOGGER.trace("Application context is collecting...");
    /*beanDefinitionRegistry = new BeanDefinitionRegistry();
      beanDefinitionReader = new DefaultBeanDefinitionReader(beanDefinitionRegistry);
      configBeanDefinitionReader = new ConfigBeanDefinitionReaderImpl(beanDefinitionRegistry);
      beanFactory = new BeanFactoryImpl(beanDefinitionRegistry);
      beanFactory.createBeans();
      beans = beanFactory.getReadyBeans();
    */
  }

  @Override
  public <T> T getBean(Class<T> beanType) throws NoUniqueBeanException {
    Map<String, T> beansOfSpecifiedType = getAllBeans(beanType);
    if (beansOfSpecifiedType.size() > 1) {
      throw new NoUniqueBeanException(beanType);
    }
    T foundBean = beansOfSpecifiedType.values().stream()
        .findFirst()
        .orElseThrow(() -> new NoSuchBeanException(String.format(
            "Bean with type %s does not exist!",
            beanType.getSimpleName())));
    LOGGER.trace("Retrieved bean with type {}", beanType.getSimpleName());
    return foundBean;
  }

  @Override
  public <T> T getBean(String name, Class<T> beanType) throws NoSuchBeanException {
    Map<String, T> beansOfSpecifiedType = getAllBeans(beanType);
    T foundBean = beansOfSpecifiedType.get(name);
    if (foundBean == null) {
      throw new NoSuchBeanException(String.format(
          "Bean with name %s, and type %s does not exist!",
          name,
          beanType.getSimpleName())
      );
    }
    LOGGER.trace("Retrieved bean with name {} and type {}", name, beanType.getSimpleName());
    return foundBean;
  }

  @Override
  public <T> Map<String, T> getAllBeans(Class<T> beanType) {
    LOGGER.trace("Retrieved all beans with type {}", beanType.getSimpleName());
    return beans.entrySet().stream()
        .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> (T) entry.getValue()));
  }

}
