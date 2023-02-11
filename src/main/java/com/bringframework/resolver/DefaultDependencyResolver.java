package com.bringframework.resolver;

import com.bringframework.annotation.Qualifier;
import com.bringframework.exception.NoSuchBeanException;
import com.bringframework.exception.NoUniqueBeanException;
import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.ConfigBeanDefinition;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link DependencyResolver} interface
 *
 * <p>Provides basic bean dependency resolution capabilities.
 * Is not capable of resolving conflicts when multiple bean candidates are available
 *
 * <p>The main purpose of the class is to define the bean name of the candidate to be inserted based on beans available
 * in the bean definitions map. If no candidate is found, a {@link NoSuchBeanException} is thrown. If multiple
 * candidates for injection are found, a {@link NoUniqueBeanException} is thrown.
 */
@Slf4j
public class DefaultDependencyResolver implements DependencyResolver {

  private final Map<String, BeanDefinition> beanDefinitions;
  private final Map<String, ConfigBeanDefinition> configBeanDefinitions;

  public DefaultDependencyResolver(BeanDefinitionRegistry definitionRegistry) {
    this.beanDefinitions = definitionRegistry.getAllBeanDefinitions();
    this.configBeanDefinitions = definitionRegistry.getAllConfigBeanDefinitions();
  }

  /**
   * Returns the name of the bean candidate to be inserted based on the available beans in the bean definitions.
   *
   * <p>If no candidate is found, a {@link NoSuchBeanException} is thrown. If multiple candidates for injection are
   * found, a {@link NoUniqueBeanException} is thrown.
   *
   * @param candidateType the desired type of the candidate
   * @param metadata      additional information that will be ignored by this implementation
   * @return the name of the bean to be used
   */
  @Override
  public String getCandidateNameOfType(Class<?> candidateType, Annotation... metadata) {
    log.debug("Resolving a candidate of type {}", candidateType.getSimpleName());
    return getQualifierValue(metadata)
        .map(name -> resolveCandidateNameByType(name, candidateType))
        .orElseGet(() -> findAndValidateCandidateByType(candidateType));
  }

  private String findAndValidateCandidateByType(Class<?> candidateType) {
    Predicate<Map.Entry<String, BeanDefinition>> validateRegularBean =
        entry -> candidateType.isAssignableFrom(entry.getValue().getBeanClass());
    Predicate<Map.Entry<String, ConfigBeanDefinition>> validateConfigBean =
        entry -> candidateType.isAssignableFrom(entry.getValue().factoryMethod().getReturnType());

    List<String> candidateNames = Stream.concat(
            beanDefinitions.entrySet().stream().filter(validateRegularBean),
            configBeanDefinitions.entrySet().stream().filter(validateConfigBean)
        )
        .map(Map.Entry::getKey)
        .toList();

    if (candidateNames.isEmpty()) {
      throw new NoSuchBeanException(candidateType);
    }
    if (candidateNames.size() > 1) {
      throw new NoUniqueBeanException(candidateType);
    }
    return candidateNames.get(0);
  }

  private String resolveCandidateNameByType(String candidateName, Class<?> candidateType) {
    Boolean isRegularBean =
        Optional.ofNullable(beanDefinitions.get(candidateName))
            .map(BeanDefinition::getBeanClass)
            .map(candidateType::isAssignableFrom)
            .orElse(false);
    Boolean isConfigBean =
        Optional.ofNullable(configBeanDefinitions.get(candidateName))
            .map(ConfigBeanDefinition::factoryMethod)
            .map(Method::getReturnType)
            .map(candidateType::isAssignableFrom)
            .orElse(false);
    if (isConfigBean || isRegularBean) {
      return candidateName;
    }
    throw new NoSuchBeanException(candidateType);
  }

  private Optional<String> getQualifierValue(Annotation... metadata) {
    return Arrays.stream(metadata)
        .filter(Qualifier.class::isInstance)
        .findFirst()
        .map(Qualifier.class::cast)
        .map(Qualifier::value);
  }

}
