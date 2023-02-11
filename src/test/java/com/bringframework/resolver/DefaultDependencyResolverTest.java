package com.bringframework.resolver;


import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bringframework.annotation.Qualifier;
import com.bringframework.exception.NoSuchBeanException;
import com.bringframework.exception.NoUniqueBeanException;
import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.ConfigBeanDefinition;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultDependencyResolverTest {
  
  private static final String LINKED_LIST_BEAN_NAME = "linkedList";
  private static final String ARRAY_LIST_BEAN_NAME = "arrayList";
  private static final String LINKED_LIST_FACTORY_METHOD_NAME = "linkedListFactoryMethod";
  private static final String ARRAY_LIST_FACTORY_METHOD_NAME = "arrayListFactoryMethod";

  @Mock
  private BeanDefinitionRegistry registry;

  @Test
  @DisplayName("NoSuchBeanException is thrown when no suitable candidates for injection were found")
  void noSuchBeanExceptionThrownWhenNoCandidateFound() {
    when(registry.getAllBeanDefinitions()).thenReturn(emptyMap());
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


  @Test
  @DisplayName("Candidate registered as a ConfigBean should be resolved by type")
  void configCandidateResolvedByType() {
    var configBeanDefinition = configBeanDefinitionOfMethod(LINKED_LIST_FACTORY_METHOD_NAME);
    when(registry.getAllBeanDefinitions()).thenReturn(emptyMap());
    when(registry.getAllConfigBeanDefinitions()).thenReturn(Map.of(LINKED_LIST_BEAN_NAME,configBeanDefinition));

    var dependencyResolver = new DefaultDependencyResolver(registry);
    assertEquals(LINKED_LIST_BEAN_NAME, dependencyResolver.getCandidateNameOfType(LinkedList.class));
  }



  @Test
  @DisplayName("Candidate registered as a DefaultBeanDefinition should be resolved with @Qualifier")
  void componentCandidateResolvedWithQualifier(){
    var linkedListBeanDefinition = mockBeanDefinitionOfClass(LinkedList.class);
    var arrayListBeanDefinition = mock(BeanDefinition.class);
    var mockQualifier = linkedListQualifier();
    Map<String, BeanDefinition> beanDefinitionMap = Map.of(
        ARRAY_LIST_BEAN_NAME, arrayListBeanDefinition,
        LINKED_LIST_BEAN_NAME, linkedListBeanDefinition
    );

    when(registry.getAllBeanDefinitions()).thenReturn(beanDefinitionMap);
    when(registry.getAllConfigBeanDefinitions()).thenReturn(emptyMap());

    var dependencyResolver = new DefaultDependencyResolver(registry);
    assertEquals(LINKED_LIST_BEAN_NAME,dependencyResolver.getCandidateNameOfType(List.class,mockQualifier));
  }

  @Test
  @DisplayName("Candidate registered as a ConfigBeanDefinition should be resolved with @Qualifier")
  void configCandidateResolvedWithQualifier() {
    var linkedListBeanDefinition = configBeanDefinitionOfMethod(LINKED_LIST_FACTORY_METHOD_NAME);
    var arrayListBeanDefinition = configBeanDefinitionOfMethod(ARRAY_LIST_FACTORY_METHOD_NAME);
    var mockQualifier = linkedListQualifier();
    Map<String,ConfigBeanDefinition> configBeanDefinitionMap = Map.of(
        LINKED_LIST_BEAN_NAME, linkedListBeanDefinition,
        ARRAY_LIST_BEAN_NAME, arrayListBeanDefinition
    );

    when(registry.getAllBeanDefinitions()).thenReturn(emptyMap());
    when(registry.getAllConfigBeanDefinitions()).thenReturn(configBeanDefinitionMap);

    var dependencyResolver = new DefaultDependencyResolver(registry);
    assertEquals(LINKED_LIST_BEAN_NAME,dependencyResolver.getCandidateNameOfType(List.class,mockQualifier));
  }

  @Test
  @DisplayName("NoSuchBeanException should be thrown when no candidate with name mentioned in @Qualifier were found")
  void noSuchBeanExceptionThrownWhenQualifierBeanNotFound(){
    var mockQualifier = linkedListQualifier();
    when(registry.getAllBeanDefinitions()).thenReturn(emptyMap());
    when(registry.getAllConfigBeanDefinitions()).thenReturn(emptyMap());

    var dependencyResolver = new DefaultDependencyResolver(registry);
    assertThrows(NoSuchBeanException.class, () -> dependencyResolver.getCandidateNameOfType(List.class,mockQualifier));
  }



  private Map<String, BeanDefinition> linkedListBeanDefinitionMap(){
    return Map.of(LINKED_LIST_BEAN_NAME, mockBeanDefinitionOfClass(LinkedList.class));
  }

  private Qualifier linkedListQualifier(){
    var mockQualifier = mock(Qualifier.class);
    when(mockQualifier.value()).thenReturn(LINKED_LIST_BEAN_NAME);
    return mockQualifier;
  }

  @SneakyThrows
  private ConfigBeanDefinition configBeanDefinitionOfMethod(String methodName){
    return new ConfigBeanDefinition(TestClass.class.getDeclaredMethod(methodName));
  }

  private BeanDefinition mockBeanDefinitionOfClass(Class<?> clazz) {
    var beanDefinition = mock(BeanDefinition.class);
    doReturn(clazz).when(beanDefinition).getBeanClass();
    return beanDefinition;
  }

  static class TestClass{
    LinkedList<?> linkedListFactoryMethod(){
      return new LinkedList<>();
    }

    ArrayList<?> arrayListFactoryMethod(){
      return new ArrayList<>();
    }
  }

}
