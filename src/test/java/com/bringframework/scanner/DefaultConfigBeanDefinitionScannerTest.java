package com.bringframework.scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bringframework.registry.BeanDefinitionRegistry;
import com.bringframework.registry.ConfigBeanDefinition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultConfigBeanDefinitionScannerTest {

  @Mock
  private BeanDefinitionRegistry registry;

  @Captor
  private ArgumentCaptor<String> configBeanDefinitionNameArgumentCaptor;

  @Captor
  private ArgumentCaptor<ConfigBeanDefinition> configBeanDefinitionArgumentCaptor;

  @InjectMocks
  private DefaultConfigBeanDefinitionScanner scanner;

  @Test
  void registerConfigBeansTest() {
    //when
    scanner.registerConfigBeans("com.bringframework.scanner");

    //then
    verify(registry, times(2)).registerConfigBeanDefinition(
        configBeanDefinitionNameArgumentCaptor.capture(),
        configBeanDefinitionArgumentCaptor.capture());

    var beanDefinitions = configBeanDefinitionArgumentCaptor.getAllValues();
    assertEquals("random", beanDefinitions.get(0).factoryMethod().getName());
    assertEquals("reflections", beanDefinitions.get(1).factoryMethod().getName());
    assertEquals(2, beanDefinitions.size());

  }
}
