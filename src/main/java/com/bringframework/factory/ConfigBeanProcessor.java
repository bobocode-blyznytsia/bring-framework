package com.bringframework.factory;

import static com.bringframework.util.BeanUtils.createInstance;

import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.ConfigBeanDefinition;
import com.bringframework.resolver.DependencyResolver;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link BeanProcessor} that is responsible for processing {@link ConfigBeanDefinition}s
 * of beans from classes marked as {@link com.bringframework.annotation.Configuration}
 * provided  from {@link BeanDefinitionRegistry}.
 *
 * <p>ConfigBeanProcessor is not responsible for injecting other beans, but {@link AutowiredBeanPostProcessor}.
 *
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class ConfigBeanProcessor implements BeanProcessor {

  private final BeanDefinitionRegistry beanDefinitionRegistry;
  private final Map<String, Object> rawBeanMap;
  private final DependencyResolver dependencyResolver;

  /**
   * {@inheritDoc}
   */
  public void process() {
    Map<String, ConfigBeanDefinition> configBeanDefinitionMap =
        beanDefinitionRegistry.getAllConfigBeanDefinitions();
    var beansSize = configBeanDefinitionMap.size();
    log.debug("Creating {} row config beans found in bean definition registry", beansSize);

    configBeanDefinitionMap.forEach(this::initializeBeanRecursively);
    log.debug("Created {} config row beans", beansSize);
  }

  private Object initializeBeanRecursively(String beanName, ConfigBeanDefinition beanDefinition) {
    var factoryMethod = beanDefinition.factoryMethod();
    var parameters = factoryMethod.getParameters();

    Object[] candidatesBeans = Arrays.stream(parameters)
        .map(parameter -> dependencyResolver.getCandidateNameOfType(parameter.getType(),
            parameter.getAnnotations()))
        .map(this::getOrInitializeBean)
        .toArray();

    Object beanInstance = createInstance(factoryMethod, candidatesBeans);
    rawBeanMap.put(beanName, beanInstance);

    return beanInstance;
  }

  private Object getOrInitializeBean(String name) {
    if (rawBeanMap.containsKey(name)) {
      return rawBeanMap.get(name);
    }
    return initializeBeanRecursively(name, beanDefinitionRegistry.getConfigBeanDefinition(name));
  }
}
