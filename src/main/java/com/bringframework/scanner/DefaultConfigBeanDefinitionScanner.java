package com.bringframework.scanner;

import com.bringframework.annotation.Bean;
import com.bringframework.annotation.Configuration;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.ConfigBeanDefinition;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

/**
 * The DefaultConfigBeanDefinitionScanner class is used to scan and register beans defined in classes annotated with the
 * {@link Configuration} annotation.
 */
@Slf4j
public class DefaultConfigBeanDefinitionScanner implements ConfigBeanDefinitionScanner {
  private final BeanDefinitionRegistry registry;

  public DefaultConfigBeanDefinitionScanner(BeanDefinitionRegistry registry) {
    this.registry = registry;
  }

  /**
   * Registers the config beans defined in the specified class path. The method scans the class path for classes
   * annotated with the {@link Configuration} annotation. For each class found, the method scans its declared methods
   * for those annotated with the {@link Bean} annotation. For each method found, the method creates a
   * ConfigBeanDefinition and registers it in the {@link BeanDefinitionRegistry}.
   *
   * @param classPath the class path to be scanned.
   */
  @Override
  public void registerConfigBeans(String classPath) {
    var reflections = new Reflections(classPath);
    var configClasses = reflections.getTypesAnnotatedWith(Configuration.class);
    log.debug("found {} classes annotated with @Configuration", configClasses.size());
    configClasses.forEach(this::registerBean);
  }

  private void registerBean(Class<?> configClass) {
    Arrays.stream(configClass.getDeclaredMethods())
        .filter(method -> method.isAnnotationPresent(Bean.class))
        .map(ConfigBeanDefinition::new)
        .forEach(configBeanDefinition -> registry.registerConfigBeanDefinition(
            configBeanDefinition.factoryMethod().getName(), configBeanDefinition));
  }
}
