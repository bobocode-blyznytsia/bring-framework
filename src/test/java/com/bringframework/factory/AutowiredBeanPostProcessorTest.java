package com.bringframework.factory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.resolver.DependencyResolver;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AutowiredBeanPostProcessorTest {

  @Mock
  DependencyResolver dependencyResolver;
  @Mock
  BeanDefinitionRegistry beanDefinitionRegistry;
  AutowiredBeanPostProcessor autowiredBPP;


  @Test
  void injectsIntoField() throws NoSuchFieldException {
    var testClassABean = new TestClassA();
    var secondBean = new TestClassB();

    var firstBeanDefinition = mock(BeanDefinition.class);
    when(firstBeanDefinition.getAutowiredFieldsMetadata()).thenReturn(Collections.emptyMap());
    when(beanDefinitionRegistry.getBeanDefinition("testClassA")).thenReturn(firstBeanDefinition);

    var secondBeanDefinition = mock(BeanDefinition.class);
    Field fieldToBeInjected = TestClassB.class.getDeclaredField("testClassA");
    when(secondBeanDefinition.getAutowiredFieldsMetadata())
        .thenReturn(Map.of("testClassA", fieldToBeInjected));
    when(beanDefinitionRegistry.getBeanDefinition("testClassB")).thenReturn(secondBeanDefinition);

    Map<String, Object> rawBeans = Map.of(
        "testClassA", testClassABean,
        "testClassB", secondBean
    );

    when(dependencyResolver.getCandidateNameOfType(TestClassA.class)).thenReturn("testClassA");
    autowiredBPP = new AutowiredBeanPostProcessor(beanDefinitionRegistry, rawBeans);
    injectMockDependencyResolver();

    autowiredBPP.process();

    Assertions.assertSame(testClassABean, secondBean.testClassA);
  }

  @SneakyThrows
  private void injectMockDependencyResolver() {
    Field dependencyResolverField =
        autowiredBPP.getClass().getDeclaredField("dependencyResolver");
    dependencyResolverField.setAccessible(true);
    dependencyResolverField.set(autowiredBPP, dependencyResolver);
    dependencyResolverField.setAccessible(false);
  }


  class TestClassA {

  }

  class TestClassB {
    TestClassA testClassA;
  }
}