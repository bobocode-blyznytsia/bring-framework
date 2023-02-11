package com.bringframework.factory;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.resolver.DependencyResolver;
import java.lang.reflect.Field;
import java.util.Map;
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

  @Test
  void injectsIntoField() throws NoSuchFieldException {
    var testClassABean = new TestClassA();
    var testClassBBean = new TestClassB();

    var firstBeanDefinition = mock(BeanDefinition.class);
    when(firstBeanDefinition.getAutowiredFieldsMetadata()).thenReturn(emptyMap());

    var secondBeanDefinition = mock(BeanDefinition.class);
    Field testClassAField = TestClassB.class.getDeclaredField(TEST_CLASS_A_FIELD_NAME);
    when(secondBeanDefinition.getAutowiredFieldsMetadata())
        .thenReturn(Map.of(TEST_CLASS_A_FIELD_NAME, testClassAField));

    when(beanDefinitionRegistry.getBeanDefinition(TEST_CLASS_A_BEAN_NAME)).thenReturn(firstBeanDefinition);
    when(beanDefinitionRegistry.getBeanDefinition(TEST_CLASS_B_BEAN_NAME)).thenReturn(secondBeanDefinition);

    Map<String, Object> rawBeans = Map.of(
        TEST_CLASS_A_BEAN_NAME, testClassABean,
        TEST_CLASS_B_BEAN_NAME, testClassBBean
    );
    Map<String, BeanDefinition> beanDefinitionMap = Map.of(
        TEST_CLASS_A_BEAN_NAME, firstBeanDefinition,
        TEST_CLASS_B_BEAN_NAME, secondBeanDefinition
    );

    when(beanDefinitionRegistry.getAllBeanDefinitions()).thenReturn(beanDefinitionMap);
    when(dependencyResolver.getCandidateNameOfType(TestClassA.class)).thenReturn(TEST_CLASS_A_BEAN_NAME);

    new AutowiredBeanPostProcessor(beanDefinitionRegistry, rawBeans,dependencyResolver).process();

    assertSame(testClassABean, testClassBBean.testClassA);
  }

  class TestClassA {

  }

  class TestClassB {
    TestClassA testClassA;
  }
}
