package com.bringframework.factory;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.resolver.DependencyResolver;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AutowiredBeanPostProcessorTest {
  private static final String TEST_CLASS_A_BEAN_NAME = "testClassA";
  private static final String TEST_CLASS_B_BEAN_NAME = "testClassB";
  private static final String TEST_CLASS_A_FIELD_NAME = "testClassA";

  @Mock
  private DependencyResolver dependencyResolver;

  @Mock
  private BeanDefinitionRegistry beanDefinitionRegistry;

  private AutowiredBeanPostProcessor autowiredBPP;

  @Test
  void injectsIntoField() throws NoSuchFieldException {
    var testClassABean = new TestClassA();
    var secondBean = new TestClassB();

//    var firstBeanDefinition = mock(BeanDefinition.class);
//    when(firstBeanDefinition.getAutowiredFieldsMetadata()).thenReturn(Collections.emptyMap());
//    when(beanDefinitionRegistry.getBeanDefinition(TEST_CLASS_A_BEAN_NAME)).thenReturn(firstBeanDefinition);
//
//    var secondBeanDefinition = mock(BeanDefinition.class);
//    Field testClassAField = TestClassB.class.getDeclaredField(TEST_CLASS_A_FIELD_NAME);
//    when(secondBeanDefinition.getAutowiredFieldsMetadata())
//        .thenReturn(Map.of(TEST_CLASS_A_FIELD_NAME, testClassAField));
//    when(beanDefinitionRegistry.getBeanDefinition(TEST_CLASS_B_BEAN_NAME)).thenReturn(secondBeanDefinition);
//
//    Map<String, Object> rawBeans = Map.of(
//        TEST_CLASS_A_BEAN_NAME, testClassABean,
//        TEST_CLASS_B_BEAN_NAME, secondBean
//    );
//
//    when(dependencyResolver.getCandidateNameOfType(TestClassA.class)).thenReturn(TEST_CLASS_A_BEAN_NAME);
//    autowiredBPP = new AutowiredBeanPostProcessor(beanDefinitionRegistry, rawBeans);
//    injectMockDependencyResolver();
//
//    autowiredBPP.process();
//
//    assertSame(testClassABean, secondBean.testClassA);
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
