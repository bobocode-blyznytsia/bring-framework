package com.bringframework.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionImpl;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RawBeanProcessorTest {

  @Mock
  private BeanDefinitionRegistry beanDefinitionRegistry;

  private RawBeanProcessor beanPostProcessor;

  private final Map<String, Object> rawBeanMap = new HashMap<>();

  @BeforeEach
  void setUp() {
    beanPostProcessor = new RawBeanProcessor(beanDefinitionRegistry, rawBeanMap);
  }

  @Test
  void shouldProcessBeanDefinitions() {
    BeanDefinition stringBeanDefinition = BeanDefinitionImpl.builder().clazz(String.class).build();
    BeanDefinition arrayListBeanDefinition =
        BeanDefinitionImpl.builder().clazz(ArrayList.class).build();

    Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    beanDefinitionMap.put("stringBean", stringBeanDefinition);
    beanDefinitionMap.put("arrayListBean", arrayListBeanDefinition);
    when(beanDefinitionRegistry.getAllBeanDefinitions()).thenReturn(beanDefinitionMap);

    beanPostProcessor.process();

    int expectedBeansSize = 2;
    assertEquals(expectedBeansSize, rawBeanMap.size());
    assertNotNull(beanDefinitionMap.get("stringBean"));
    assertNotNull(beanDefinitionMap.get("arrayListBean"));
  }

}
