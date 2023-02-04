package com.bobocode.factory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bringframework.factory.BeanPostProcessor;
import com.bringframework.factory.RawBeanProcessor;
import com.bringframework.factory.impl.BeanFactoryImpl;
import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BeanFactoryImplTest {

  @Mock
  private BeanDefinitionRegistry beanDefinitionRegistry;

//  @Mock
//  private BeanPostProcessor beanPostProcessor;

  private final Map<String, Object> rawBeansMap = new ConcurrentHashMap<>();

  @Test
  void shouldProcessBeanDefinitions() {
    //given
    BeanDefinition stringBeanDefinition = mock(BeanDefinition.class);
    when(stringBeanDefinition.<String>getBeanClass()).thenReturn(String.class);
    Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    beanDefinitionMap.put("str", stringBeanDefinition);
    when(beanDefinitionRegistry.getAllBeanDefinitions()).thenReturn(beanDefinitionMap);

    BeanFactoryImpl beanFactory = new BeanFactoryImpl(beanDefinitionRegistry);
    RawBeanProcessor rawBeanProcessor = new RawBeanProcessor(beanDefinitionRegistry, rawBeansMap);
    beanFactory.setRawBeanProcessor(rawBeanProcessor);

    //when
    Map<String, Object> readyBeans = beanFactory.createBeans();

    //then
    Assertions.assertEquals(1, readyBeans.size() );
    Assertions.assertTrue(readyBeans.containsKey("str"));
  }
}
