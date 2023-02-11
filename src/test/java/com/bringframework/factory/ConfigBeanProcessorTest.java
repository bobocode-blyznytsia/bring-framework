package com.bringframework.factory;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.ConfigBeanDefinition;
import com.bringframework.resolver.DependencyResolver;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ConfigBeanProcessorTest {

  @Mock
  private BeanDefinitionRegistry beanDefinitionRegistry;

  @Mock
  private Map<String, Object> rawBeanMap;

  @Mock
  private DependencyResolver dependencyResolver;

  private ConfigBeanProcessor configBeanProcessor;


  @BeforeEach
  public void setUp() throws NoSuchMethodException {
    MockitoAnnotations.openMocks(this);
    configBeanProcessor = new ConfigBeanProcessor(beanDefinitionRegistry, rawBeanMap, dependencyResolver);
    Class<TestConfigClass> configClass = TestConfigClass.class;

    Method method1 = configClass.getMethod("bean1");
    Method method2 = configClass.getMethod("bean2", TestClass.class);
    method1.setAccessible(true);
    method2.setAccessible(true);

    ConfigBeanDefinition beanDefinition1 = new ConfigBeanDefinition(method1);
    ConfigBeanDefinition beanDefinition2 = new ConfigBeanDefinition(method2);

    Map<String, ConfigBeanDefinition> configBeanDefinitionMap = new HashMap<>();
    configBeanDefinitionMap.put("bean1", beanDefinition1);
    configBeanDefinitionMap.put("bean2", beanDefinition2);

    when(beanDefinitionRegistry.getAllConfigBeanDefinitions()).thenReturn(configBeanDefinitionMap);
    when(beanDefinitionRegistry.getConfigBeanDefinition("bean1")).thenReturn(beanDefinition1);
    when(beanDefinitionRegistry.getConfigBeanDefinition("bean2")).thenReturn(beanDefinition2);

    when(dependencyResolver.getCandidateNameOfType(any(), any())).thenReturn("bean1", "bean2");
  }


  @Test
  void testProcess() {
    // When
    configBeanProcessor.process();

    // Then
    verify(beanDefinitionRegistry, times(1)).getAllConfigBeanDefinitions();
    verify(rawBeanMap, times(3)).put(anyString(), any());
    verify(dependencyResolver, times(1)).getCandidateNameOfType(any());
  }

  @Test
  void testInitializeBeanRecursive() {
    // Given
    when(rawBeanMap.containsKey("bean1")).thenReturn(false);
    when(rawBeanMap.get("bean1")).thenReturn(new TestClass());

    // When
    configBeanProcessor.process();

    // Then
    verify(beanDefinitionRegistry).getAllConfigBeanDefinitions();
    verify(dependencyResolver).getCandidateNameOfType(any());
    verify(rawBeanMap).put(eq("bean2"), any(TestClass.class));
    verify(rawBeanMap, times(2)).put(eq("bean1"), any(TestClass.class));
    verify(rawBeanMap, times(1)).containsKey(anyString());
  }

}

class TestConfigClass {

  public TestConfigClass() {
  }

  public TestClass bean1() {
    return new TestClass();
  }

  public TestClass bean2(TestClass bean1) {
    return bean1;
  }
}

class TestClass {

}