package com.bringframework.reader;

import com.bringframework.annotations.Bean;
import com.bringframework.annotations.Configuration;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.ConfigBeanDefinition;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

//TODO Add and fix JavaDocs
@Slf4j
public class DefaultConfigBeanDefinitionReader implements ConfigBeanDefinitionReader {
  private final BeanDefinitionRegistry registry;

  public DefaultConfigBeanDefinitionReader(BeanDefinitionRegistry registry) {
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
