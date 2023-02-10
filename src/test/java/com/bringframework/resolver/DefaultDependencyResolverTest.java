package com.bringframework.resolver;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bringframework.exceptions.NoSuchBeanException;
import com.bringframework.exceptions.NoUniqueBeanException;
import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultDependencyResolverTest {
  
  private static final String LINKED_LIST_BEAN_NAME = "linkedList";
  private static final String ARRAY_LIST_BEAN_NAME = "arrayList";

  @Mock
  BeanDefinitionRegistry registry;
  @Test
  @DisplayName("NoSuchBeanException is thrown when no suitable candidates for injection were found")
  void noSuchBeanExceptionThrownWhenNoCandidateFound() {
    when(registry.getAllBeanDefinitions()).thenReturn(Collections.emptyMap());
    var dependencyResolver = new DefaultDependencyResolver(registry);
    var desiredCandidateType = Object.class;
    assertThrows(NoSuchBeanException.class,
        () -> dependencyResolver.getCandidateNameOfType(desiredCandidateType));
  }

  @Test
  @DisplayName("NoUniqueBeanException is thrown when there are multiple candidates for injection")
  void noUniqueBeanExceptionThrownWhenMultipleCandidateFound() {
    var linkedListBeanDefinition = mockBeanDefinitionOfClass(LinkedList.class);
    var arrayListBeanDefinition = mockBeanDefinitionOfClass(ArrayList.class);

    Map<String, BeanDefinition> beanDefinitionMap = Map.of(
        ARRAY_LIST_BEAN_NAME, arrayListBeanDefinition,
        LINKED_LIST_BEAN_NAME, linkedListBeanDefinition
    );
    when(registry.getAllBeanDefinitions()).thenReturn(beanDefinitionMap);
    var dependencyResolver = new DefaultDependencyResolver(registry);
    var desiredCandidateType = List.class;
    assertThrows(NoUniqueBeanException.class, () -> dependencyResolver.getCandidateNameOfType(desiredCandidateType));
  }

  @Test
  @DisplayName("Candidate should be resolved when looking for the candidate of the same type")
  void candidateResolvedForSameType() {
    Map<String, BeanDefinition> beanDefinitionMap = linkedListBeanDefinitionMap();
    when(registry.getAllBeanDefinitions()).thenReturn(beanDefinitionMap);
    var dependencyResolver = new DefaultDependencyResolver(registry);
    assertEquals(LINKED_LIST_BEAN_NAME, dependencyResolver.getCandidateNameOfType(LinkedList.class));
  }

  @Test
  @DisplayName("Candidate of subtype should be resolved when looking for the candidate of superclass")
  void candidateResolvedForSubtype() {
    Map<String, BeanDefinition> beanDefinitionMap = linkedListBeanDefinitionMap();
    when(registry.getAllBeanDefinitions()).thenReturn(beanDefinitionMap);
    var dependencyResolver = new DefaultDependencyResolver(registry);
    assertEquals(LINKED_LIST_BEAN_NAME, dependencyResolver.getCandidateNameOfType(AbstractList.class));
  }

  @Test
  @DisplayName("Interface implementing candidate should be resolved when looking for the interface")
  void candidateResolvedForInterfaceImplementation() {
    Map<String, BeanDefinition> beanDefinitionMap = linkedListBeanDefinitionMap();
    when(registry.getAllBeanDefinitions()).thenReturn(beanDefinitionMap);
    var dependencyResolver = new DefaultDependencyResolver(registry);
    assertEquals(LINKED_LIST_BEAN_NAME, dependencyResolver.getCandidateNameOfType(List.class));
  }

  private Map<String, BeanDefinition> linkedListBeanDefinitionMap(){
    return Map.of(LINKED_LIST_BEAN_NAME, mockBeanDefinitionOfClass(LinkedList.class));
  }

  private BeanDefinition mockBeanDefinitionOfClass(Class<?> clazz) {
    var beanDefinition = mock(BeanDefinition.class);
    doReturn(clazz).when(beanDefinition).getBeanClass();
    return beanDefinition;
  }

}
