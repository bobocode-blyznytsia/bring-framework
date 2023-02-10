package com.bringframework.scanner;

import com.bringframework.annotation.Bean;
import com.bringframework.annotation.Configuration;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.ConfigBeanDefinition;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

//TODO Add and fix JavaDocs
@Slf4j
public class DefaultConfigBeanDefinitionScanner implements ConfigBeanDefinitionScanner {
  private final BeanDefinitionRegistry registry;

  public DefaultConfigBeanDefinitionScanner(BeanDefinitionRegistry registry) {
    this.registry = registry;
  }

  //TODO Add and fix JavaDocs
  @Override
  public void registerConfigBeans(String classPath) {
    var reflections = new Reflections(classPath);
    var configClasses = reflections.getTypesAnnotatedWith(Configuration.class);
    log.debug("found {} classes annotated with @Configuration", configClasses.size());
    configClasses.parallelStream().forEach(this::processConfigClass);
  }

  private void processConfigClass(Class<?> configClass) {
    Arrays.stream(configClass.getDeclaredMethods())
        .filter(method -> method.isAnnotationPresent(Bean.class))
        .map(ConfigBeanDefinition::new)
        .forEach(configBeanDefinition -> registry.registerConfigBeanDefinition(
            configBeanDefinition.factoryMethod().getName(), configBeanDefinition));
  }
}
