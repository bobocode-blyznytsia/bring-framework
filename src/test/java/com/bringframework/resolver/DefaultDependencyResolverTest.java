package com.bringframework.resolver;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bringframework.exceptions.NoSuchBeanException;
import com.bringframework.exceptions.NoUniqueBeanException;
import com.bringframework.registry.BeanDefinition;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultDependencyResolverTest {

  @Test
  void noCandidateFound_NoSuchBeanExceptionThrown() {
    var dependencyResolver = new DefaultDependencyResolver(Collections.emptyMap());
    var desiredCandidateType = Object.class;
    assertThrows(NoSuchBeanException.class,
        () -> dependencyResolver.getCandidateNameOfType(desiredCandidateType));
  }

  @Test
  void multipleCandidateFound_NoUniqueBeanExceptionThrown() {
    var linkedListBeanDefinition = mock(BeanDefinition.class);
    when(linkedListBeanDefinition.<LinkedList>getBeanClass()).thenReturn(LinkedList.class);

    var arrayListBeanDefinition = mock(BeanDefinition.class);
    when(arrayListBeanDefinition.<ArrayList>getBeanClass()).thenReturn(ArrayList.class);

    Map<String, BeanDefinition> beanDefinitionMap = Map.of(
        "arrayList", arrayListBeanDefinition,
        "linkedList", linkedListBeanDefinition
    );

    var dependencyResolver = new DefaultDependencyResolver(beanDefinitionMap);
    var desiredCandidateType = List.class;
    assertThrows(NoUniqueBeanException.class,
        () -> dependencyResolver.getCandidateNameOfType(desiredCandidateType));
  }

  @Test
  void candidateResolvedForSameType() {
    var linkedListBeanDefinition = mock(BeanDefinition.class);
    when(linkedListBeanDefinition.<LinkedList>getBeanClass()).thenReturn(LinkedList.class);

    var expectedCandidateName = "linkedList";
    Map<String, BeanDefinition> beanDefinitionMap =
        Map.of(expectedCandidateName, linkedListBeanDefinition);

    var dependencyResolver = new DefaultDependencyResolver(beanDefinitionMap);

    assertEquals(expectedCandidateName,
        dependencyResolver.getCandidateNameOfType(LinkedList.class));
  }

  @Test
  void candidateResolvedForSubtype() {
    var linkedListBeanDefinition = mock(BeanDefinition.class);
    when(linkedListBeanDefinition.<LinkedList>getBeanClass()).thenReturn(LinkedList.class);

    var expectedCandidateName = "linkedList";
    Map<String, BeanDefinition> beanDefinitionMap =
        Map.of(expectedCandidateName, linkedListBeanDefinition);

    var dependencyResolver = new DefaultDependencyResolver(beanDefinitionMap);

    assertEquals(expectedCandidateName,
        dependencyResolver.getCandidateNameOfType(AbstractList.class));
  }

  @Test
  void candidateResolvedForInterfaceImplementation() {
    var linkedListBeanDefinition = mock(BeanDefinition.class);
    when(linkedListBeanDefinition.<LinkedList>getBeanClass()).thenReturn(LinkedList.class);

    var expectedCandidateName = "linkedList";
    Map<String, BeanDefinition> beanDefinitionMap =
        Map.of(expectedCandidateName, linkedListBeanDefinition);

    var dependencyResolver = new DefaultDependencyResolver(beanDefinitionMap);

    assertEquals(expectedCandidateName,
        dependencyResolver.getCandidateNameOfType(List.class));
  }

}