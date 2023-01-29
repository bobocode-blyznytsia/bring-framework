package com.bringframework.reader;

import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

import com.bringframework.annotations.Autowired;
import com.bringframework.annotations.Component;
import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionImpl;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.lang.reflect.Field;
import java.util.Arrays;
import lombok.SneakyThrows;
import org.reflections.Reflections;

/**
 * Default implementation of {@link BeanDefinitionReader}.
 * This Reader scans provided package name and registers found {@link BeanDefinition}s
 * in a {@link BeanDefinitionRegistry}. By default, it looks for classes annotated
 * with {@link Component} annotation.
 */
public class DefaultBeanDefinitionReader implements BeanDefinitionReader {

  protected BeanDefinitionRegistry registry;

  public DefaultBeanDefinitionReader(BeanDefinitionRegistry registry) {
    this.registry = registry;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SneakyThrows
  public void registerBeans(String packageName) {
    var reflections = new Reflections(packageName);
    var beanClasses = reflections.getTypesAnnotatedWith(Component.class);
    var definitionsMap =
        beanClasses
            .stream()
            .collect(toMap(this::resolveBeanName, this::createBeanDefinition));

    definitionsMap.forEach((key, value) -> registry.registerBeanDefinition(key, value));
  }

  private String resolveBeanName(Class<?> beanClass) {
    var explicitName = beanClass.getAnnotation(Component.class).value();
    return isBlank(explicitName) ? uncapitalize(beanClass.getSimpleName()) : explicitName;
  }

  private BeanDefinition createBeanDefinition(Class<?> beanClass) {
    Field[] declaredFields = beanClass.getDeclaredFields();
    var autowiredFields =
        Arrays.stream(declaredFields)
            .filter(field -> nonNull(field.getAnnotation(Autowired.class)))
            .collect(toMap(Field::getName, identity()));
    return BeanDefinitionImpl
        .builder()
        .clazz(beanClass)
        .autowiredFieldsMetadata(autowiredFields)
        .build();
  }
}