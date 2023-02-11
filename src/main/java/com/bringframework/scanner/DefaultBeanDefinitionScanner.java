package com.bringframework.scanner;

import static java.util.Objects.nonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

import com.bringframework.annotation.Autowired;
import com.bringframework.annotation.Component;
import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.lang.reflect.Field;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

/**
 * Default implementation of {@link BeanDefinitionScanner}.
 * This Scanner reads provided package name and registers found {@link BeanDefinition}s
 * in a {@link BeanDefinitionRegistry}. By default, it looks for classes annotated
 * with {@link Component} annotation.
 */
@Slf4j
public class DefaultBeanDefinitionScanner implements BeanDefinitionScanner {

  private final BeanDefinitionRegistry registry;

  public DefaultBeanDefinitionScanner(BeanDefinitionRegistry registry) {
    this.registry = registry;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void registerBeans(String packageName) {
    var reflections = new Reflections(packageName);
    var beanClasses = reflections.getTypesAnnotatedWith(Component.class);
    log.debug("Found {} classes annotated with @Component", beanClasses.size());
    beanClasses.forEach(this::registerBean);
  }

  private void registerBean(Class<?> beanClass) {
    var beanName = resolveBeanName(beanClass);
    var beanDefinition = createBeanDefinition(beanClass);
    registry.registerBeanDefinition(beanName, beanDefinition);
    log.debug("Bean {} registered in beanDefinitionRegistry", beanName);
  }

  private String resolveBeanName(Class<?> beanClass) {
    var explicitName = beanClass.getAnnotation(Component.class).value();
    return isBlank(explicitName) ? uncapitalize(beanClass.getSimpleName()) : explicitName;
  }

  private BeanDefinition createBeanDefinition(Class<?> beanClass) {
    Field[] declaredFields = beanClass.getDeclaredFields();
    var autowiredFieldsMap =
        Arrays.stream(declaredFields)
            .filter(field -> nonNull(field.getAnnotation(Autowired.class)))
            .collect(toMap(Field::getName, identity()));
    log.debug("Found {} autowired fields for bean {}", autowiredFieldsMap.size(),
        resolveBeanName(beanClass));
    return BeanDefinition
        .builder()
        .clazz(beanClass)
        .autowiredFieldsMetadata(autowiredFieldsMap)
        .build();
  }
}
