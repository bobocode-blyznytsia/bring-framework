package com.bringframework.resolver;

import com.bringframework.exceptions.NoSuchBeanException;
import com.bringframework.exceptions.NoUniqueBeanException;
import com.bringframework.registry.BeanDefinition;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link DependencyResolver} interface
 *
 * <p>Provides basic bean dependency resolution capabilities.
 * Is not capable of resolving conflicts when multiple bean candidates are available
 */

@Slf4j
public class DefaultDependencyResolver implements DependencyResolver {

  private final Map<String, BeanDefinition> beanDefinitions;

  public DefaultDependencyResolver(Map<String, BeanDefinition> beanDefinitions) {
    this.beanDefinitions = new HashMap<>(beanDefinitions);
  }

  /**
   * Defines bean name of the candidate to be inserted based on beans available in beanDefinitions
   * If no candidate found - {@link NoSuchBeanException} is thrown
   * If multiple candidates for injection were found - {@link NoUniqueBeanException} is thrown
   *
   * @param candidateType desired type of the candidate
   * @param metadata      additional information that will be ignored by this implementation
   * @return name of the bean to be used
   */
  @Override
  public String getCandidateNameOfType(Class<?> candidateType, Annotation... metadata) {
    log.debug("Resolving a candidate of type {}", candidateType.getSimpleName());
    List<String> candidateNames = beanDefinitions.entrySet().stream()
        .filter(entry -> candidateType.isAssignableFrom(entry.getValue().getBeanClass()))
        .map(Map.Entry::getKey)
        .toList();

    if (candidateNames.isEmpty()) {
      throw new NoSuchBeanException("No bean of type " + candidateType + " found");
    }
    if (candidateNames.size() > 1) {
      throw new NoUniqueBeanException(candidateType);
    }
    return candidateNames.get(0);
  }


}
