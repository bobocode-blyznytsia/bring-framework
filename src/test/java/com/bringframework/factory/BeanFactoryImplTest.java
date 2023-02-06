package com.bringframework.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.bringframework.factory.impl.BeanFactoryImpl;
import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionImpl;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BeanFactoryImplTest {

  @Mock
  private BeanDefinitionRegistry beanDefinitionRegistry;

  @Test
  void shouldProcessBeanDefinitions() {
    //given
    BeanDefinition stringBeanDefinition = BeanDefinitionImpl.builder().clazz(String.class).build();
    Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    beanDefinitionMap.put("str", stringBeanDefinition);
    when(beanDefinitionRegistry.getAllBeanDefinitions()).thenReturn(beanDefinitionMap);

    BeanFactoryImpl beanFactory = new BeanFactoryImpl(beanDefinitionRegistry);

    //when
    Map<String, Object> readyBeans = beanFactory.createBeans();

    //then
    assertEquals(1, readyBeans.size());
    assertTrue(readyBeans.containsKey("str"));
  }
}
