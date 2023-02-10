package com.bringframework.factory;

import static com.bringframework.util.BeanUtils.createInstance;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.ConfigBeanDefinition;
import com.bringframework.resolver.DefaultDependencyResolver;
import java.util.Arrays;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Creates instances of beans by provided {@link BeanDefinition}s from {@link BeanDefinitionRegistry}. RawBeanProcessor
 * is not responsible for injecting other beans, but {@link AutowiredBeanPostProcessor}.
 */

//TODO Add and fix JavaDocs
@Slf4j
public class ConfigBeanProcessor {

  private final Map<String, Object> rawBeanMap;

  private final BeanDefinitionRegistry beanDefinitionRegistry;

  public ConfigBeanProcessor(BeanDefinitionRegistry beanDefinitionRegistry, Map<String, Object> rawBeanMap) {
    this.beanDefinitionRegistry = beanDefinitionRegistry;
    this.rawBeanMap = rawBeanMap;
  }

  //TODO Fix JavaDocs
  public void process() {
    Map<String, ConfigBeanDefinition> configBeanDefinitionMap = beanDefinitionRegistry.getAllConfigBeanDefinitions();
    log.debug("Started creating {} config beans without dependencies found in bean definition " + "registry.",
        configBeanDefinitionMap.size());
    for (Map.Entry<String, ConfigBeanDefinition> entry : configBeanDefinitionMap.entrySet()) {
      initializeBeanRecursive(entry.getKey(), entry.getValue());

    }
    log.debug("Finished creating config beans.");//todo add some information like count of created beans
  }

  private Object initializeBeanRecursive(String beanName, ConfigBeanDefinition beanDefinition) {
    var factoryMethod = beanDefinition.factoryMethod();
    var parameters = factoryMethod.getParameters();

    DefaultDependencyResolver dependencyResolver = new DefaultDependencyResolver(beanDefinitionRegistry);

    Object[] candidatesBeans = Arrays.stream(parameters)
        .map(parameter -> dependencyResolver.getCandidateNameOfType(parameter.getType(), parameter.getAnnotations()))
        .map(this::getOrInitializeBean).toArray();

    Object beanInstance = createInstance(factoryMethod, candidatesBeans);
    rawBeanMap.put(beanName, beanInstance);

    return beanInstance;
  }

  private Object getOrInitializeBean(String name) {
    if (rawBeanMap.containsKey(name)) {
      return rawBeanMap.get(name);
    }
    return initializeBeanRecursive(name, beanDefinitionRegistry.getConfigBeanDefinition(name));
  }
}
