package com.bringframework.resolver;

import java.lang.annotation.Annotation;


/**
 * Interface for defining a candidate for bean injection based on desired candidate type and
 * metadata present
 */
public interface DependencyResolver {
  /**
   * Finds a candidate of desired candidateType or its subclasses and provides its bean name
   *
   * @param candidateType desired type of the candidate
   * @param metadata      additional information that may affect decision-making
   * @return bean name of the candidate to be injected
   */
  String getCandidateNameOfType(Class<?> candidateType, Annotation... metadata);
}

