package com.bringframework.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BeanFactoryImplTest {

  @Mock
  private BeanDefinitionRegistry beanDefinitionRegistry;

  @InjectMocks
  private BeanFactoryImpl beanFactory;

  @Test
  void shouldProcessBeanDefinitions() {
    //given
    BeanDefinition stringBeanDefinition = BeanDefinition.builder()
        .clazz(String.class)
        .autowiredFieldsMetadata(Collections.emptyMap())
        .build();
    Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    beanDefinitionMap.put("str", stringBeanDefinition);
    when(beanDefinitionRegistry.getAllBeanDefinitions()).thenReturn(beanDefinitionMap);
    when(beanDefinitionRegistry.getBeanDefinition("str")).thenReturn(stringBeanDefinition);

    //when
    Map<String, Object> readyBeans = beanFactory.createBeans();

    //then
    assertEquals(1, readyBeans.size());
    assertTrue(readyBeans.containsKey("str"));
  }
}
